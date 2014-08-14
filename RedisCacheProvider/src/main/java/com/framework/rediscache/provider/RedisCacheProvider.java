package com.framework.rediscache.provider;

import com.framework.cache.spi.cache.ICache;
import com.framework.cache.spi.cache.RollBackCache;
import com.framework.cache.spi.provider.CacheProvider;
import com.framework.cache.spi.config.CacheConfig;
import com.framework.rediscache.cache.RedisCache;

import com.frameworkLog.factory.LogFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.Set;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;

/**
 *
 * @author nelson
 */
public class RedisCacheProvider implements CacheProvider {

    private static final Logger logger = LogFactory.getInstance().getLogger(RedisCacheProvider.class);
    private Class<RollBackCache> rollbackCacheClazz;
    private static Jedis cache;
    private static String IP = "192.168.1.105";
    private static int PORT = 6379;
    private static String PASSWORD = "123456";
    private static int TIMEOUT = 1000;

    static {
        Reader reader = null;
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("rediscache.properties");
        Properties property = new Properties();
        try {
            reader = new InputStreamReader(is, "UTF-8");
            property.load(reader);
            IP = property.getProperty("ip");
            PORT = Integer.parseInt(property.getProperty("port"));
            PASSWORD = property.getProperty("password");
            TIMEOUT = Integer.parseInt(property.getProperty("connectionTimeout"));
        } catch (Exception ex) {
            throw new RuntimeException("加载配置redissession.properties出错", ex);
        }
        logger.debug("IP={}", IP);
        logger.debug("PORT={}", PORT);
        logger.debug("PASSWORD={}", PASSWORD);
        logger.debug("TIMEOUT={}", TIMEOUT);
        cache = new Jedis(IP, PORT, TIMEOUT);
        cache.auth(PASSWORD);
        logger.info("清楚所有的缓存。。。。。");
        cache.flushAll();
        Set<String> keys = cache.keys("*");
        logger.debug("keys:{}", keys);
        if (!keys.isEmpty()) {
            cache.del(keys.toArray(new String[]{}));
        }
        keys = cache.hkeys("*");
        logger.debug("hkeys:{}", keys);
        if (!keys.isEmpty()) {
            cache.del(keys.toArray(new String[]{}));
        }
    }

    public ICache buildCache(String name, CacheConfig cacheConfig) {
        ICache ehCache = new RedisCache(cache);
        return ehCache;
    }

    public RollBackCache buildRollBackCache() {

        RollBackCache rollBackCache = null;
        try {
            rollBackCache = rollbackCacheClazz.newInstance();
        } catch (InstantiationException ex) {
            logger.error("init CacheValueClass error:" + ex);
            throw new RuntimeException("init RollBackCache Error");
        } catch (IllegalAccessException ex) {
            logger.error("init CacheValueClass error:" + ex);
            throw new RuntimeException("init RollBackCache Error");
        }

        return rollBackCache;
    }

    /**
     * @param rollbackCacheClazz the rollbackCacheClazz to set
     */
    public void setRollbackCacheClazz(Class<RollBackCache> rollbackCacheClazz) {
        this.rollbackCacheClazz = rollbackCacheClazz;
    }
}
