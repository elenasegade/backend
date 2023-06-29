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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.thrift.TException;
import org.ignis.backend.cluster.IExecutor;
import org.ignis.backend.cluster.ITaskContext;
import org.ignis.backend.cluster.tasks.ICache;
import org.ignis.backend.cluster.tasks.ICache.Level;
import org.ignis.backend.exception.IExecutorExceptionWrapper;
import org.ignis.backend.exception.IgnisException;
import org.ignis.rpc.IExecutorException;
import org.slf4j.LoggerFactory;
/**
 * @author César Pomar
 */
public class ICacheTask extends IExecutorTask {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IFlatmapTask.class);

    private final ICache cache;

    private final String directoryCriu;

    public ICacheTask(String name, IExecutor executor, ICache cache) {
        super(name, executor);
        this.cache = cache;
        String directory=executor.getContainer().getProperties().getProperty("ignis.dfs.home") + "/" 
                + executor.getContainer().getProperties().getProperty("ignis.job.name")+"/checkpointing";
        Path path = Paths.get(directory);
        this.directoryCriu = directory;
        try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    /*Obtiene el pid del proceso padre*/
    private Long getPidParent(){
        ProcessHandle currentProcess = ProcessHandle.current();
        Long pid = currentProcess.parent().get().pid();
        
        return pid;
    }
    private void writeSockets(String socket, String fd,BufferedWriter bw) throws IOException{
        bw.write(" --inherit-fd fd["+fd+"]:socket:["+socket+"]");
    }
    /*Función que recoje todos los sockets para realizar el checkpointing*/
    private String getSockets(String pid) throws IOException{
        Process p=Runtime.getRuntime().exec("lsof -U +c 0");
        String commandDump = "nohup criu dump -v4 -D " + this.directoryCriu + " -t " + pid + " --tcp-established --leave-running --shell-job --ext-unix-sk";
        BufferedWriter bw=new BufferedWriter(new FileWriter(directoryCriu+"/sockets.txt"));
        try(BufferedReader br= new BufferedReader (new InputStreamReader(p.getInputStream()))){
            String out;
            br.readLine();
            while ((out = br.readLine()) != null) {
                // Parsear la línea de salida de lsof para obtener el número del socket UNIX
                String[] words = out.split("\\s+");
                String socket = words[7];
                String fd = words[3];
                commandDump = commandDump + " --external unix[" + socket + "]";
                writeSockets(socket, fd, bw);
            }
        }

        commandDump = commandDump + " &";
        LOGGER.info(commandDump);
        bw.close();
        return commandDump;
    }
        
    /*Función que realiza el checkpointing del proceso*/
    private void doCheckpointing(String pid){
        try {
            //Se define la ruta donde se guardará el checkpointing
            String cDumpp=getSockets(pid);

            //Se realiza el checkpointing en la ruta especificada y el pid del proceso
          
            Process pDump = Runtime.getRuntime().exec(new String[] { "bash", "-c", cDumpp});
            File f= new File(directoryCriu);
            
            //Para ver la salida del comando
            while(f.list().length==0){
                 try {
                    Thread.sleep(20);
                }
                catch (Exception e) {
                    LOGGER.error("CRIU error: ",e);
                }
            }

            // Leer la salida de error del proceso
            String line;
            InputStream stderr = pDump.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(stderr));
            while ((line = errorReader.readLine()) != null) {
                LOGGER.info(line);
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
            if(cache.getNextLevel().equals(ICache.Level.CHECKPOINT)){
                Long pid = getPidParent();
                doCheckpointing(pid.toString());
                executor.getCacheContextModule().cache(cache.getId(), (byte) Level.DISK.getInt());
            }else{
                executor.getCacheContextModule().cache(cache.getId(), (byte) cache.getNextLevel().getInt());
            }
        } catch (IExecutorException ex) {
            throw new IExecutorExceptionWrapper(ex);
        } catch (TException ex) {
            throw new IgnisException(ex.getMessage(), ex);
        }
        LOGGER.info(log() + "Cache updated");
    }

}
