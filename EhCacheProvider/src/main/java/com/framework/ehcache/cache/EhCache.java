package com.framework.ehcache.cache;

import com.framework.cache.spi.cache.ICache;
import com.framework.cache.spi.key.CacheKey;
import com.framework.cache.spi.value.CacheValue;
import com.framework.ehcache.value.EhCacheValue;
import com.frameworkLog.factory.LogFactory;
import java.util.List;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class EhCache<T> implements ICache {

    private static final Logger logger = LogFactory.getInstance().getLogger(EhCache.class);
    private Cache cache;

    public EhCache(Cache cache) {
        this.cache = cache;
    }

    public CacheValue getValue(CacheKey key) {
        EhCacheValue cacheValue = null;
        // CacheEnum cacheEnum = key.getCacheEnum();
        Element element = cache.get(key);
        logger.debug("cache hit {}", cache.getStatistics().cacheHitRatio());
        if (element != null) {
            cacheValue = (EhCacheValue) element.getObjectValue();
        }
        return cacheValue;
    }

    public void putValue(CacheKey key, CacheValue value) {
        logger.debug("cache hit {}", cache.getStatistics().cacheHitRatio());
        Element element = new Element(key, value);
        cache.put(element);
    }

    public void remove(CacheKey key) {
        logger.debug("cache hit {}", cache.getStatistics().cacheHitRatio());
        cache.remove(key);
    }

    public void clear() {
        cache.removeAll();
    }

    public int size() {
        return cache.getSize();
    }

    public void synchronize(CacheKey key, CacheValue value) {
        logger.debug("cache hit {}", cache.getStatistics().cacheHitRatio());
        cache.remove(key);
        Element element = new Element(key, value);
        cache.put(element);
    }

    public void removeByTableNames(String[] tableNames) {
        List<CacheKey> cacheKeys = cache.getKeys();
        logger.debug("cache hit {}", cache.getStatistics().cacheHitRatio());
        String[] relatedTableNames;
        boolean isExist;
        for (CacheKey cacheKey : cacheKeys) {
            isExist = false;
            relatedTableNames = cacheKey.getTableName();
            for (String tableName : tableNames) {
                for (String relatedTable : relatedTableNames) {
                    if (tableName.equals(relatedTable)) {
                        isExist = true;
                        break;
                    }
                }
            }
            if (isExist) {
                cache.remove(cacheKey);
            }
        }
    }

    public void removeByCacheNames(String[] cacheNames) {
        CacheManager cacheManager = CacheManager.getInstance();
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.removeAll();
            }
        }
    }

    public void removeAll() {
        cache.removeAll();
    }
}
