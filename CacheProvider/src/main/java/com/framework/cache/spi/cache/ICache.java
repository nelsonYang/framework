package com.framework.cache.spi.cache;

import com.framework.cache.spi.key.CacheKey;
import com.framework.cache.spi.value.CacheValue;

/**
 *
 * @author nelson
 */
public interface ICache<K extends CacheKey,V extends CacheValue> {
    public V getValue(K key);
    public void putValue(K key,V value);
    public void remove(K key);
    public void clear();
    public int size();
    public void synchronize(K key,V value);
    public void removeByTableNames(String[] tableNames);
    public void removeByCacheNames(String[] cacheNames);
    public void removeAll();

 
    
}
