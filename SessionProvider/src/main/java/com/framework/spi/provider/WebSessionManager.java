package com.framework.spi.provider;

import com.framework.spi.entity.SessionEntity;

/**
 *
 * @author nelson
 */
public interface WebSessionManager {

    public boolean putKey(String key, SessionEntity value);
    
    public boolean putKey(String key,SessionEntity value, int timeoutTime);
    
    public String getKey(String key);

    public void removeTimeout(long sessionTimeout);

    public boolean clearKey(String key);
    
    public SessionEntity getSessionEntityByKey(String key);
}
