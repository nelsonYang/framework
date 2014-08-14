package com.framework.cache.spi.key;

import com.framework.cache.spi.cachetype.CacheEnum;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface CacheKey {
    public CacheKey setTableName(String[] tableName);
    public CacheKey setSql(String sql);
    public CacheKey setParameters(Map<String,String> parameters);
    public CacheKey setPrimaryKey(String[] primaryKey);
   
    public CacheKey setValues(String[] values);
    public String getKey();
    public CacheKey setCacheEnum(CacheEnum cacheEnum);
    public CacheEnum getCacheEnum();
    public String[] getTableName();

}
