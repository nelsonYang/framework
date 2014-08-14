package com.framework.threadpool;

import com.framework.enumeration.ThreadPoolEnum;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author nelson
 */
public final class ThreadPoolManager {

    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    private static final Map<ThreadPoolEnum, ExecutorService> threadPoolMap = new HashMap<ThreadPoolEnum, ExecutorService>(2, 1);
    
    private ThreadPoolManager() {
    }
    
    static {
        threadPoolMap.put(ThreadPoolEnum.SCHEDULED, Executors.newScheduledThreadPool(3));
        threadPoolMap.put(ThreadPoolEnum.FIXED, Executors.newFixedThreadPool(20));
    }
    
    public static synchronized ThreadPoolManager getInstance() {
        if (threadPoolManager == null) {
            threadPoolManager = new ThreadPoolManager();
        }
        return threadPoolManager;
    }
    
    public ExecutorService getExecuteService(ThreadPoolEnum threadPoolEnum) {
        return threadPoolMap.get(threadPoolEnum);
    }
}
