package com.framework.spi.provider;

import com.framework.spi.entity.CodeEntity;

/**
 *
 * @author nelson
 */
public interface CodeManager {

    public boolean putKey(String key, CodeEntity value);
    
    public boolean putKey(String key, CodeEntity value, int timeout);

    public String getKey(String key);

    public boolean remove(String key);

    public void removeTimeout(long timeout);
}
