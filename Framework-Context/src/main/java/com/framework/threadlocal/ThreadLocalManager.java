package com.framework.threadlocal;

/**
 *
 * @author Administrator
 */
public class ThreadLocalManager <T>{
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
