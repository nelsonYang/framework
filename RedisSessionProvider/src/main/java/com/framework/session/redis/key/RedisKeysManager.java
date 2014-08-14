package com.framework.session.redis.key;

import com.framework.session.redis.config.RedisConfig;
import com.framework.spi.provider.KeysManager;
import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;

/**
 *
 * @author nelson
 */
public class RedisKeysManager implements KeysManager {

    private final Logger logger = LogFactory.getInstance().getLogger(RedisKeysManager.class);
    private static final String KEYS_PREFIX = "KEY_";

    @Override
    public boolean putKey(String key, String value) {
        logger.debug("putKey key:{}", key);
        boolean isSuccess = false;
        logger.debug("putKey value:{}", value);
        if (value != null && !value.isEmpty()) {
            Jedis keysJedis = RedisConfig.buildSessionJedis();
            keysJedis.set(KEYS_PREFIX + key, value);
            isSuccess = true;
        }
        logger.debug("putKey isSuccess:{}", isSuccess);
        return isSuccess;
    }

    @Override
    public String getKey(String key) {
        logger.debug("getKey key:{}", key);
        String value = null;
        Jedis keysJedis = RedisConfig.buildSessionJedis();
        value = keysJedis.get(KEYS_PREFIX + key);
        logger.debug("getKey value:{}", value);
        return value;
    }

    @Override
    public boolean putKey(String key, String value, int timeoutTime) {
        logger.debug("putKey value:{}", value);
        logger.debug("putKey key:{}", key);
        logger.debug("putKey timeoutTime:{}", timeoutTime);
        boolean isSuccess = false;
        if (value != null && !value.isEmpty()) {
            Jedis keysJedis = RedisConfig.buildSessionJedis();
            keysJedis.setex(KEYS_PREFIX + key, timeoutTime, value);
            isSuccess = true;
        }
        logger.debug("putKey isSuccess:{}", isSuccess);
        return isSuccess;
    }
}
