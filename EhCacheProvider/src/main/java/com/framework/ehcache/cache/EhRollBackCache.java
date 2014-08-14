package com.framework.ehcache.cache;

import com.framework.cache.spi.cache.ICache;
import com.framework.cache.spi.cache.RollBackCache;
import com.framework.cache.spi.key.CacheKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author nelson
 */
public class EhRollBackCache implements RollBackCache {

    private final Map<ICache, List<CacheKey>> entityCacheMap = new HashMap<ICache, List<CacheKey>>();
    private final Map<ICache, List<String[]>> sqlCacheMap = new HashMap<ICache, List<String[]>>();

    public void putEntityCacheKey(ICache entityCache, CacheKey cacheKey) {
        List<CacheKey> cacheKeyList = entityCacheMap.get(entityCache);
        if (cacheKeyList != null) {
            cacheKeyList.add(cacheKey);
        } else {
            cacheKeyList = new ArrayList<CacheKey>();
            cacheKeyList.add(cacheKey);
            entityCacheMap.put(entityCache, cacheKeyList);
        }
    }

    public void putSqlCacheKey(ICache entityCache, String[] tables) {
        List<String[]> tableList = sqlCacheMap.get(entityCache);
        if (tableList != null) {
            tableList.add(tables);
        } else {
            tableList = new ArrayList<String[]>();
            tableList.add(tables);
            sqlCacheMap.put(entityCache, tableList);
        }
    }

    public void rollback() {
        ICache entityCache;
        ICache sqlCache;
        List<String[]> tableLists;
        List<CacheKey> cacheKeyList;
        for (Entry<ICache, List<CacheKey>> entry : entityCacheMap.entrySet()) {
            entityCache = entry.getKey();
            cacheKeyList = entry.getValue();
            for (CacheKey cacheKey : cacheKeyList) {
                entityCache.remove(cacheKey);
            }
        }
        for (Entry<ICache, List<String[]>> entry : sqlCacheMap.entrySet()) {
            sqlCache = entry.getKey();
            tableLists = entry.getValue();
            for (String[] tables : tableLists) {
                sqlCache.removeByTableNames(tables);
            }
        }

    }
   
}
