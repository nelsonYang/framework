package com.framework.session.local.key;

import com.framework.spi.provider.KeysManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author nelson
 */
public class LocalKeysManager implements KeysManager{

    private final Map<String, String> keysMap = new ConcurrentHashMap<String, String>();

    @Override
    public boolean putKey(String key, String value) {
        boolean isSuccess = false;
        keysMap.put(key, value);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public String getKey(String key) {
        String value = null;
        value = keysMap.get(key);
        return value;
    }

    @Override
    public boolean putKey(String key, String value, int timeoutTime) {
        return this.putKey(key, value);
    }
}
