package com.framework.entity.threadlocal;

/**
 *
 * @author Administrator
 */
public class RollBackCacheThreadLocalManager <T>{
    private final ThreadLocal<T> threadLocal = new ThreadLocal<T>();
    
    public void openThreadLocal(T t){
        threadLocal.set(t);
    }
    
    public T getValue(){
        return threadLocal.get();
    }
    
    public void closeThreadLocal(){
        threadLocal.remove();
    }
}
