/*
 * Copyright (C) 2018
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ignis.backend.cluster.tasks.executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.thrift.TException;
import org.ignis.backend.cluster.IExecutor;
import org.ignis.backend.cluster.ITaskContext;
import org.ignis.backend.cluster.tasks.ICache;
import org.ignis.backend.cluster.tasks.ICache.Level;
import org.ignis.backend.exception.IExecutorExceptionWrapper;
import org.ignis.backend.exception.IgnisException;
import org.ignis.rpc.IExecutorException;
import org.slf4j.LoggerFactory;

import java.lang.Thread;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
/**
 * @author César Pomar
 */
public class ICacheTask extends IExecutorTask {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IFlatmapTask.class);

    private final ICache cache;

    private final String directorioCriu;

    public ICacheTask(String name, IExecutor executor, ICache cache) {
        super(name, executor);
        this.cache = cache;
        String rutaCarpeta=System.getProperty("user.dir")+"/checkpointing";
        Path path = Paths.get(rutaCarpeta);
        if(!Files.exists(path)){
            try{
                Files.createDirectory(path);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        this.directorioCriu = rutaCarpeta;
    }
    /*Obtiene el pid del proceso padre*/
    private Long ontenerPidPadre(){
        ProcessHandle currentProcess = ProcessHandle.current();
        Long pid = currentProcess.parent().get().pid();
        LOGGER.error("Pid del padre "+ " " + pid);
        
        return pid;
    }
    private void escribirSockets(String socket, BufferedWriter bw) throws IOException{
        bw.write(socket+" ");
        System.out.println(socket+" ");
    }
    /*Función que recoje todos los sockets para realizar el checkpointing*/
    private String obtenerSockets(String pid) throws IOException{
        Process p=Runtime.getRuntime().exec("lsof -U +c 0");
        String comandoDump = "nohup criu dump -D " + this.directorioCriu + " -t " + pid + " --tcp-established --leave-running --shell-job --ext-unix-sk";
        BufferedWriter bw=new BufferedWriter(new FileWriter(directorioCriu+"/sockets.txt"));
        try(BufferedReader br= new BufferedReader (new InputStreamReader(p.getInputStream()))){
            String salida;
            br.readLine();
            while ((salida = br.readLine()) != null) {
                // Parsear la línea de salida de lsof para obtener el número del socket UNIX
                String[] letras = salida.split("\\s+");
                String socket = letras[7];
                comandoDump = comandoDump + " --external unix[" + socket + "]";
                escribirSockets(socket, bw);
            }
        }

        comandoDump = comandoDump + " > /tmp/criu.log &";
        System.err.println(comandoDump);
        bw.close();
        return comandoDump;
    }
        
    /*Función que realiza el checkpointing del proceso*/
    private void realizarCheckpointing(String pid){
        try {
            //Se define la ruta donde se guardará el checkpointing
            String cDumpp=obtenerSockets(pid);

            //Se realiza el checkpointing en la ruta especificada y el pid del proceso
          
            Process pDump = Runtime.getRuntime().exec(new String[] { "bash", "-c", cDumpp});
            File f= new File(directorioCriu);
            
            //Para ver la salida del comando
            while(f.list().length==0){
                 try {
                    Thread.sleep(20);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            InputStream stdout= new FileInputStream("/tmp/criu.log");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
            String line;
            while ((line = reader.readLine()) != null) {
                System.err.println(line);
            }

            // Leer la salida de error del proceso
            InputStream stderr = pDump.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(stderr));
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }

            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(ITaskContext context) throws IgnisException {
        LOGGER.info(log() + "updating cache from " + cache.getActualLevel() + " to " + cache.getNextLevel());
        try {
            if (cache.getActualLevel().equals(ICache.Level.NO_CACHE) &&
                    cache.getNextLevel().equals(ICache.Level.NO_CACHE)) {
                executor.getCacheContextModule().loadCache(cache.getId());
                executor.getCacheContextModule().cache(cache.getId(), (byte) ICache.Level.NO_CACHE.getInt());
            }
            if(cache.getActualLevel()==Level.CHECKPOINT){
                Long pid = ontenerPidPadre();
                realizarCheckpointing(pid.toString());
                cache.setActualLevel(Level.NO_CACHE);
            }
            executor.getCacheContextModule().cache(cache.getId(), (byte) cache.getNextLevel().getInt());
        } catch (IExecutorException ex) {
            throw new IExecutorExceptionWrapper(ex);
        } catch (TException ex) {
            throw new IgnisException(ex.getMessage(), ex);
        }
        LOGGER.info(log() + "Cache updated");
    }

}
