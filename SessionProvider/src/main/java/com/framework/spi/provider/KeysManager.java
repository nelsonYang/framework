package com.framework.spi.provider;

/**
 *
 * @author nelson
 */
public interface KeysManager {

    public boolean putKey(String key, String value);

    public boolean putKey(String key, String value, int timeoutTime);

    public String getKey(String key);
}
