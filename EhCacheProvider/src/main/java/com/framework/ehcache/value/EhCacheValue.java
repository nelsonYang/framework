package com.framework.ehcache.value;

import com.framework.cache.spi.value.CacheValue;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class EhCacheValue<T> implements CacheValue<T> {

    private T element;
    public List<Map<String, String>> getMapList() {
        return (List<Map<String, String>>) element;
    }

//    public T getEntity() {
//        return  (T)getElement();
//    }

    public void put(T t) {
        element = t;
    }

    public Map<String, String> getMap() {
        return (Map<String, String>) element;
    }

//    /**
//     * @return the element
//     */
//    public T getElement() {
//        return element;
//    }
//
//    /**
//     * @param element the element to set
//     */
//    public void setElement(T element) {
//        this.element = element;
//    }

    
}
