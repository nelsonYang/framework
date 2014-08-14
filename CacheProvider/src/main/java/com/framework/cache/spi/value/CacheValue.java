package com.framework.cache.spi.value;

import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface CacheValue <T> {
    public List<Map<String,String>> getMapList();
    public Map<String,String> getMap();
   // public T getEntity();
    public void put(T t);
    
   
  
  //  public List<T> getEntityList();
    
}
