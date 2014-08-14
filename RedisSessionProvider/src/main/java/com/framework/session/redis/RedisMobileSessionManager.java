package com.framework.session.redis;

import com.alibaba.fastjson.JSON;
import static com.framework.session.redis.RedisWebSessionManager.WEB_SESSION_PREFIX;
import com.framework.session.redis.config.RedisConfig;
import com.framework.spi.entity.SessionEntity;
import com.framework.spi.provider.SessionManager;
import com.frameworkLog.factory.LogFactory;
import java.util.Set;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;

/**
 *
 * @author Administrator
 */
public class RedisMobileSessionManager implements SessionManager {

    private final Logger logger = LogFactory.getInstance().getLogger(RedisMobileSessionManager.class);
    public static final String MOBILE_SESSION_PREFIX = "MOBILE_SESSION_";

    @Override
    public void removeTimeout(long sessionTimeout) {
    }

    @Override
    public boolean putKey(String key, SessionEntity value) {
        logger.debug("putKey key:{}", key);
        boolean isSuccess = false;
        String json = JSON.toJSONString(value);
        logger.debug("putKey json:{}", json);
        if (json != null && !json.isEmpty()) {
            Jedis sessionJedis = RedisConfig.buildSessionJedis();
            sessionJedis.set(MOBILE_SESSION_PREFIX + key, json);
            isSuccess = true;
        }
        logger.debug("putKey isSuccess:{}", isSuccess);
        return isSuccess;
    }

    @Override
    public String getKey(String key) {
        logger.debug("getKey key:{}", key);
        String session = null;
        Jedis sessionJedis = RedisConfig.buildSessionJedis();
        String jsonStr = sessionJedis.get(MOBILE_SESSION_PREFIX + key);
        logger.debug("getKey jsonStr:{}", jsonStr);
        if (jsonStr != null && !jsonStr.isEmpty()) {
            SessionEntity sessionEntity = JSON.parseObject(jsonStr, SessionEntity.class);
            if (sessionEntity != null) {
                session = sessionEntity.getSession();
            }
        }
        logger.debug("getKey session:{}", session);
        return session;
    }

    @Override
    public boolean clearKey(String key) {
        logger.debug("clearKey key:{}", key);
        boolean isSuccess = false;
        Jedis sessionJedis = RedisConfig.buildSessionJedis();
        Set<String> keys = sessionJedis.keys(MOBILE_SESSION_PREFIX + "*");
        if (!keys.isEmpty()) {
            sessionJedis.del(keys.toArray(new String[]{}));
        }
        isSuccess = true;
        logger.debug("clearKey isSuccess:{}", isSuccess);
        return isSuccess;
    }

    @Override
    public SessionEntity getSessionEntityByKey(String key) {
        logger.debug("getSessionEntityByKey key:{}", key);
        SessionEntity sessionEntity = null;
        Jedis sessionJedis = RedisConfig.buildSessionJedis();
        String jsonStr = sessionJedis.get(MOBILE_SESSION_PREFIX + key);
        if (jsonStr != null && !jsonStr.isEmpty()) {
            sessionEntity = JSON.parseObject(jsonStr, SessionEntity.class);
        }
        logger.debug("getSessionEntityByKey sessionEntity:{}", sessionEntity);
        return sessionEntity;
    }

    @Override
    public boolean putKey(String key, SessionEntity value, int timeoutTime) {
        logger.debug("putKey key:{}", key);
        boolean isSuccess = false;
        String json = JSON.toJSONString(value);
        logger.debug("putKey json:{}", json);
        logger.debug("putKey timeoutTime:{}", timeoutTime);
        if (json != null && !json.isEmpty()) {
            Jedis sessionJedis = RedisConfig.buildSessionJedis();
            sessionJedis.setex(WEB_SESSION_PREFIX + key, timeoutTime, json);
            isSuccess = true;
        }
        logger.debug("putKey isSuccess:{}", isSuccess);
        return isSuccess;
    }
}
