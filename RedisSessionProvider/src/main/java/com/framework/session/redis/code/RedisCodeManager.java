package com.framework.session.redis.code;

import com.alibaba.fastjson.JSON;
import com.framework.session.redis.config.RedisConfig;
import com.framework.spi.entity.CodeEntity;
import com.framework.spi.provider.CodeManager;
import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;

/**
 *
 * @author Administrator
 */
public class RedisCodeManager implements CodeManager {

    private final Logger logger = LogFactory.getInstance().getLogger(RedisCodeManager.class);
    public static final String CODE_PREFIX = "CODE_";

    @Override
    public boolean putKey(String key, CodeEntity value) {
        logger.debug("putKey key:{}", key);
        boolean isSuccess = false;
        String json = JSON.toJSONString(value);
        logger.debug("putKey json:{}", json);
        if (json != null && !json.isEmpty()) {
            Jedis codeJedis = RedisConfig.buildSessionJedis();
            codeJedis.set(CODE_PREFIX + key, json);
            isSuccess = true;
        }
        logger.debug("putKey isSuccess:{}", isSuccess);
        return isSuccess;
    }

    @Override
    public String getKey(String key) {
        logger.debug("getKey key:{}", key);
        String code = null;
        Jedis codeJedis = RedisConfig.buildSessionJedis();
        String jsonStr = codeJedis.get(CODE_PREFIX + key);
        logger.debug("getKey jsonStr:{}", jsonStr);
        if (jsonStr != null && !jsonStr.isEmpty()) {
            CodeEntity codeEntity = JSON.parseObject(jsonStr, CodeEntity.class);
            if (codeEntity != null) {
                code = codeEntity.getCode();
            }
        }
        logger.debug("getKey code:{}", code);
        return code;
    }

    @Override
    public boolean remove(String key) {
        logger.debug("remove key:{}", key);
        boolean isSuccess = false;
        Jedis codeJedis = RedisConfig.buildSessionJedis();
        codeJedis.del(CODE_PREFIX + key);
        logger.debug("remove isSuccess:{}", isSuccess);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public void removeTimeout(long timeout) {
    }

    @Override
    public boolean putKey(String key, CodeEntity value, int timeout) {
        logger.debug("putKey key:{}", key);
        boolean isSuccess = false;
        String json = JSON.toJSONString(value);
        logger.debug("putKey json:{}", json);
        logger.debug("putKey timeout:{}", timeout);
        if (json != null && !json.isEmpty()) {
            Jedis codeJedis = RedisConfig.buildSessionJedis();
            codeJedis.setex(CODE_PREFIX + key, timeout, json);
            isSuccess = true;
        }
        logger.debug("putKey isSuccess:{}", isSuccess);
        return isSuccess;
    }
}
