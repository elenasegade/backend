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

import org.ignis.backend.cluster.IExecutor;
import org.ignis.backend.cluster.ITaskContext;
import org.ignis.backend.cluster.tasks.IBarrier;
import org.ignis.backend.exception.IgnisException;
import org.ignis.rpc.ISource;
import org.slf4j.LoggerFactory;

/**
 *
 * @author César Pomar
 */
public final class IImportDataTask extends IExecutorContextTask {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(IImportDataTask.class);

    public static class Shared {

        public Shared(int sources, int targets) {
            this.sources = sources;
            this.targets = targets;
            barrier = new IBarrier(sources + targets);
        }

        private final int sources;
        private final int targets;
        private final IBarrier barrier;
    }

    private final Shared shared;
    private final boolean source;
    private final Long partitions;
    private final ISource src;

    public IImportDataTask(String name, IExecutor executor, Shared shared, boolean source, Long partitions, ISource src) {
        super(name, executor, source ? Mode.LOAD : Mode.SAVE);
        this.shared = shared;
        this.source = source;
        this.partitions = partitions;
        this.src = src;
    }
//TODO 

    /*
    *Select an executor in the same machine
     */
 /* private IExecutor nextHostExecutor(List<IExecutor> executors, IExecutor source) {
        String host = source.getContainer().getHost();
        for (int i = 0; i < executors.size(); i++) {
            if (executors.get(i).getContainer().getHost().equals(host)) {
                return executors.remove(i);
            }
        }
        return executors.remove(executors.size() - 1);
    }

    private void distribution() {
        long total = shared.count.values().stream().reduce(0l, (a, b) -> a + b);
        int boxs = targets.size();

        long[] splits = new long[boxs];
        int size = (int) (total / boxs);
        int mod = (int) (total % boxs);
        for (int i = 0; i < boxs; i++) {
            if (i < mod) {
                splits[i] = size + 1;
            } else {
                splits[i] = size;
            }
        }

        if (sources.equals(targets)) {
            int i = 0;
            for (long ecount : shared.count.values()) {
                if ((splits[i] - splits[i] * ratio) > ecount || ecount > (splits[i] + splits[i] * ratio)) {
                    break;
                }
                i++;
            }
            if (shared.count.size() == i) {
                return;
            }
        }

        List<IExecutor> orderTargets = new ArrayList<>();
        List<IExecutor> remainingTargets = new ArrayList<>(targets);
        IExecutor actualTarget = null;
        for (int i = 0, j = 0; i < sources.size(); i++) {
            IExecutor source = sources.get(i);
            long elements = shared.count.get(source);
            shared.msgs.put(source, new LinkedHashMap<>());
            while (elements > 0) {
                if (actualTarget == null) {
                    //if the current executor provides less elements than the next
                    if (splits[j] - elements <= 0 || splits[j] - 2 * elements <= 0) {
                        actualTarget = nextHostExecutor(remainingTargets, source);
                    } else {
                        actualTarget = nextHostExecutor(remainingTargets, sources.get(i + 1));
                    }
                    orderTargets.add(actualTarget);
                }

                if (splits[j] - elements > 0) {
                    splits[j] -= elements;
                    shared.msgs.get(source).put(actualTarget, elements);
                    elements = 0;
                } else {
                    elements -= splits[j];
                    shared.msgs.get(source).put(actualTarget, splits[j]);
                    splits[j] = 0;
                    actualTarget = null;
                    j++;
                }
            }
        }
        //Reorder Executors
        targets.clear();
        targets.addAll(orderTargets);
    }*/
    @Override
    public void run(ITaskContext context) throws IgnisException {
        /*try {//TODO
            if (barrier.await() == 0) {
                shared.count.clear();
                shared.msgs.clear();
            }
            if (type == SHUFFLE) {
                LOGGER.info(log() + "Executing shuffle");
            } else {
                LOGGER.info(log() + "Executing import");
            }
            barrier.await();
            if (type == SEND || type == SHUFFLE) {
                LOGGER.info(log() + "Counting elements");
                shared.count.put(executor, executor.getStorageModule().count());
                LOGGER.info(log() + shared.count.get(executor) + " elements");
            }
            if (barrier.await() == 0) {
                LOGGER.info(log() + "Calculating element distribution");
                distribution();
            }
            barrier.await();
            if (shared.msgs.isEmpty()) {
                LOGGER.info(log() + "Aborting, shuffle is not necessary");
                return;
            }

            if (type == SEND || type == SHUFFLE) {
                LOGGER.info(log() + "Creating " + shared.msgs.get(executor).size() + " partitions");
                int i = 1;
                IAddrManager addrManager = new IAddrManager();
                List<ISplit> splits = new ArrayList<>();
                for (Map.Entry<IExecutor, Long> msg : shared.msgs.get(executor).entrySet()) {
                    String addr = addrManager.parseAddr(executor, msg.getKey());
                    long msgId = executor.getId() * shared.count.size() + msg.getKey().getId();
                    splits.add(new ISplit(msgId, addr, msg.getValue()));
                    LOGGER.info(log() + "Partition " + (i++) + " with " + msg.getValue() + " elements to " + addr);
                }
                executor.getShuffleModule().createSplits(splits);
                LOGGER.info(log() + "Partitions created");
            }
            barrier.await();
            try {
                if (type == RECEIVE || type == SHUFFLE) {
                    LOGGER.info(log() + "Preparing to recive partitions");
                    executor.getPostmanModule().start();
                }
                barrier.await();
                if (type == SEND || type == SHUFFLE) {
                    LOGGER.info(log() + "Preparing to send partitions");
                    executor.getPostmanModule().sendAll();
                    LOGGER.info(log() + "Partitions sent");
                }
                barrier.await();
            } finally {
                if (type == RECEIVE || type == SHUFFLE) {
                    executor.getPostmanModule().stop();
                    LOGGER.info(log() + "Partitions received");
                }
            }
            if (type == RECEIVE || type == SHUFFLE) {
                List<Long> order = sources.stream().filter(e -> shared.msgs.get(e).containsKey(executor))
                        .map(e -> e.getJob()).sorted().collect(Collectors.toList());
                LOGGER.info(log() + "Joining partitions");
                executor.getShuffleModule().joinSplits(order);
                LOGGER.info(log() + "Partitions joined");
            }
            barrier.await();
            if (type == SHUFFLE) {
                LOGGER.info(log() + "Shuffle executed");
            } else {
                LOGGER.info(log() + "Import executed");
            }
        } catch (IgnisException ex) {
            barrier.fails();
            throw ex;
        } catch (BrokenBarrierException ex) {
            //Other Task has failed
        } catch (Exception ex) {
            barrier.fails();
            throw new IgnisException(ex.getMessage(), ex);
        }*/
    }

}
