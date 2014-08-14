package com.framework.cache.spi.provider;

import com.framework.cache.spi.cache.ICache;
import com.framework.cache.spi.cache.RollBackCache;
import com.framework.cache.spi.config.CacheConfig;

/**
 *
 * @author nelson
 */
public interface CacheProvider {

    public ICache buildCache(String name, CacheConfig cacheConfig);

    public RollBackCache buildRollBackCache();

    public void setRollbackCacheClazz(Class<RollBackCache> rollbackCacheClazz);
}
