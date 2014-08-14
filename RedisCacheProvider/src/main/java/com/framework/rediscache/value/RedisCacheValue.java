package com.framework.rediscache.value;

import com.framework.cache.spi.value.CacheValue;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class RedisCacheValue<T> implements CacheValue<T> {

    private T element;
    public List<Map<String, String>> getMapList() {
        return (List<Map<String, String>>) element;
    }
    public void put(T t) {
        element = t;
    }

    public Map<String, String> getMap() {
        return (Map<String, String>) element;
    }
}
