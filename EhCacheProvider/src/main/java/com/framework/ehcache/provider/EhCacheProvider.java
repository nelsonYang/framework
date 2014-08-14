package com.framework.ehcache.provider;

import com.framework.cache.spi.cache.ICache;
import com.framework.cache.spi.cache.RollBackCache;
import com.framework.cache.spi.provider.CacheProvider;
import com.framework.cache.spi.config.CacheConfig;
import com.framework.ehcache.cache.EhCache;
import com.frameworkLog.factory.LogFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class EhCacheProvider implements CacheProvider {

    private static final Logger logger = LogFactory.getInstance().getLogger(EhCacheProvider.class);
    private Class<RollBackCache> rollbackCacheClazz;
    private static final CacheManager cacheManager;

    static {
        //创建缓存管理对象
        final Configuration ehcacheConfig = new Configuration();
        ehcacheConfig.setName("yinpiao-cache");
        ehcacheConfig.setDynamicConfig(false);
        ehcacheConfig.setUpdateCheck(false);
        ehcacheConfig.setMonitoring("OFF");
        cacheManager = CacheManager.create(ehcacheConfig);

    }

    public ICache buildCache(String name, CacheConfig cacheConfig) {
        ICache ehCache;
        if (cacheManager.cacheExists(name)) {
            Cache cache = cacheManager.getCache(name);
            ehCache = new EhCache(cache);
        } else {
            CacheConfiguration cacheConfiguration = new CacheConfiguration();
            cacheConfiguration.name(cacheConfig.getTableName()).maxElementsInMemory((int) cacheConfig.getMaxElementsInMemory()).timeToIdleSeconds(cacheConfig.getTimeToIdleSeconds()).timeToLiveSeconds(cacheConfig.getTimeToLiveSeconds());
            Cache cache = new Cache(cacheConfiguration);
            cacheManager.addCache(cache);
            ehCache = new EhCache(cache);
        }
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
