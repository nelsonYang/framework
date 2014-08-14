package com.framework.cache.spi.cache;

import com.framework.cache.spi.key.CacheKey;

/**
 *
 * @author nelson
 */
public interface RollBackCache {
    public void putEntityCacheKey(ICache entityCache,CacheKey cacheKey);
    public void putSqlCacheKey(ICache entityCache,String[] tables);
    public void rollback();
  }
