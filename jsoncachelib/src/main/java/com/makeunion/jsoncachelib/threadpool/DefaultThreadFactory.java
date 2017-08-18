package com.makeunion.jsoncachelib.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    private final int threadPriority;

    DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
        this.threadPriority = threadPriority;
        group = Thread.currentThread().getThreadGroup();
        namePrefix = threadNamePrefix + poolNumber.getAndIncrement()
                + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix
                + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon())
            t.setDaemon(false);
        t.setPriority(threadPriority);
        return t;
    }
}
