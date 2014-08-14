package com.framework.rediscache.cache;

import com.alibaba.fastjson.JSON;
import com.framework.cache.spi.cache.ICache;
import com.framework.cache.spi.cachetype.CacheEnum;
import com.framework.cache.spi.key.CacheKey;
import com.framework.cache.spi.value.CacheValue;
import com.framework.rediscache.value.RedisCacheValue;
import com.frameworkLog.factory.LogFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 *
 * @author nelson
 */
public class RedisCache<T> implements ICache {

    private static final Logger logger = LogFactory.getInstance().getLogger(RedisCache.class);
    private Jedis cache;

    public RedisCache(Jedis cache) {
        this.cache = cache;
    }

    public CacheValue getValue(CacheKey key) {
        RedisCacheValue cacheValue = null;
        String cacheKey = key.getKey();
        if (logger.isDebugEnabled()) {
            logger.debug(" getValue cacheKey:{}", cacheKey);
            logger.debug(" getValue cacheEnum:{}", key.getCacheEnum());
        }
        try {
            if (key.getCacheEnum() == CacheEnum.ENTITY_CACHE) {
                Map<String, String> cacheMap = cache.hgetAll(cacheKey);
                if (cacheMap != null && !cacheMap.isEmpty()) {
                    cacheValue = new RedisCacheValue();
                    cacheValue.put(cacheMap);
                }
            } else {
                String jsonStr = cache.get(cacheKey);
                logger.debug("getValue jsonStr:{}", jsonStr);
                if (jsonStr != null) {
                    cacheValue = new RedisCacheValue();
                    // convert json to maplist
                    List resultMapList = JSON.parseArray(jsonStr, Map.class);
                    cacheValue.put(resultMapList);
                }
            }
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
        }
        return cacheValue;
    }

    public void putValue(CacheKey key, CacheValue value) {
        if (logger.isDebugEnabled()) {
            logger.debug(" putValue cacheKey:{}", key.getKey());
            logger.debug(" putValue cacheEnum:{}", key.getCacheEnum());
        }
        try {
            if (key.getCacheEnum() == CacheEnum.ENTITY_CACHE) {
                cache.hmset(key.getKey(), value.getMap());
            } else {
                List<Map<String, String>> resultMapList = value.getMapList();
                if (resultMapList != null) {
                    //covert to json
                    String jsonStr = JSON.toJSONString(resultMapList);
                    logger.debug("putValue jsonStr:{}", jsonStr);
                    cache.set(key.getKey(), jsonStr);
                }
            }
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
        }

    }

    public void remove(CacheKey key) {
        if (logger.isDebugEnabled()) {
            logger.debug(" remove cacheKey:{}", key.getKey());
            logger.debug(" remove cacheEnum:{}", key.getCacheEnum());
        }
        try {
            if (key.getCacheEnum() == CacheEnum.ENTITY_CACHE) {
                Map<String, String> entityMap = cache.hgetAll(key.getKey());
                if (entityMap != null && !entityMap.isEmpty()) {
                    cache.hdel(key.getKey(), entityMap.keySet().toArray(new String[]{}));
                }
            } else {
                cache.del(key.getKey());
            }
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
        }
    }

    public void clear() {
        try {
            cache.flushAll();
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
        }
    }

    public int size() {
        try {
            return cache.dbSize().intValue();
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
            return 0;
        }

    }

    public void synchronize(CacheKey key, CacheValue value) {
        if (logger.isDebugEnabled()) {
            logger.debug(" synchronize cacheKey:{}", key.getKey());
            logger.debug(" synchronize cacheEnum:{}", key.getCacheEnum());
        }
        try {
            if (key.getCacheEnum() == CacheEnum.ENTITY_CACHE) {
                Map<String, String> entityMap = cache.hgetAll(key.getKey());
                if (entityMap != null && !entityMap.isEmpty()) {
                    cache.hdel(key.getKey(), entityMap.keySet().toArray(new String[]{}));
                }
                cache.hmset(key.getKey(), value.getMap());
            } else {
                List<Map<String, String>> resultMapList = value.getMapList();
                cache.del(key.getKey());
                if (resultMapList != null) {
                    String jsonStr = JSON.toJSONString(resultMapList);
                    logger.debug(" synchronize jsonStr:{}", jsonStr);
                    cache.set(key.getKey(), jsonStr);
                }
            }
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
        }
    }

    public void removeByTableNames(String[] tableNames) {
        Set<String> keySet;
        try {
            for (String tableName : tableNames) {
                logger.debug(" removeByTableNames tableName:{}", tableName);
                keySet = cache.keys("*" + tableName + "*");
                logger.debug(" removeByTableNames keys keySet:{}", keySet);
                for (String key : keySet) {
                    cache.del(key);
                }
            }
            for (String tableName : tableNames) {
                logger.debug(" removeByTableNames tableName:{}", tableName);
                keySet = cache.hkeys("*" + tableName + "*");
                logger.debug(" removeByTableNames hkeys keySet:{}", keySet);
                Map<String, String> entityMap;
                for (String key : keySet) {
                    entityMap = cache.hgetAll(key);
                    if (entityMap != null && !entityMap.isEmpty()) {
                        cache.hdel(key, entityMap.keySet().toArray(new String[]{}));
                    }
                    cache.del(key);
                }
            }
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
        }
    }

    public void removeAll() {
        try {
            cache.flushAll();
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
        }
    }

    public void removeByCacheNames(String[] cacheNames) {
        Set<String> keySet;
        try {
            for (String tableName : cacheNames) {
                logger.debug(" removeByTableNames tableName:{}", tableName);
                keySet = cache.keys("*" + tableName + "*");
                logger.debug(" removeByTableNames keys keySet:{}", keySet);
                for (String key : keySet) {
                    cache.del(key);
                }
            }
            for (String tableName : cacheNames) {
                logger.debug(" removeByTableNames tableName:{}", tableName);
                keySet = cache.hkeys("*" + tableName + "*");
                logger.debug(" removeByTableNames hkeys keySet:{}", keySet);
                Map<String, String> entityMap;
                for (String key : keySet) {
                    entityMap = cache.hgetAll(key);
                    if (entityMap != null && !entityMap.isEmpty()) {
                        cache.hdel(key, entityMap.keySet().toArray(new String[]{}));
                    }
                    cache.del(key);
                }
            }
        } catch (JedisConnectionException ex) {
            logger.error("redis cannot connect....", ex);
        }
    }
}
