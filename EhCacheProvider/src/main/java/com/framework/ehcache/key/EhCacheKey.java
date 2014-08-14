package com.framework.ehcache.key;

import com.framework.cache.spi.cachetype.CacheEnum;
import com.framework.cache.spi.key.CacheKey;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class EhCacheKey implements CacheKey {

    private String key;
    private String[] tableName;
    private String sql;
    private Map<String, String> parameters;
    private String[] primaryKey;
    private CacheEnum cacheEnum;
    private String[] values;

    public String getKey() {
        StringBuilder keyBuilder = new StringBuilder();
        for (String table : tableName) {
            keyBuilder.append(table);
        }
        if (cacheEnum == CacheEnum.ENTITY_CACHE) {
            for (String keyField : primaryKey) {
                keyBuilder.append(keyField).append("-");
            }
            this.key = keyBuilder.toString();
        } else {
            keyBuilder.append(sql);
            keyBuilder.append("-");
            for (String value : values) {
                keyBuilder.append(value).append(",");
            }
            key = keyBuilder.toString();
        }
        return this.key;
    }

    public EhCacheKey setTableName(String[] tableName) {
        this.tableName = tableName;
        return this;
    }

    public EhCacheKey setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public EhCacheKey setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;

    }

    public EhCacheKey setCacheEnum(CacheEnum cacheEnum) {
        this.cacheEnum = cacheEnum;
        return this;
    }

    /**
     * @return the tableName
     */
    public String[] getTableName() {
        return tableName;
    }

    /**
     * @return the sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @return the parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * @return the primaryKey
     */
    public String[] getPrimaryKey() {
        return primaryKey;
    }

    /**
     * @return the cacheEnum
     */
    public CacheEnum getCacheEnum() {
        return cacheEnum;
    }

    public CacheKey setPrimaryKey(String[] primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public CacheKey setValues(String[] values) {
        this.values = values;
        return this;
    }
 /**
  * 必须重写hashcode和equal方法
  * @return 
  */
    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + this.getKey().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof CacheKey) {
            CacheKey cacheKey = (CacheKey) obj;
            if (this.getKey().equals(cacheKey.getKey())) {
                isEqual = true;
            }
        }
        return isEqual;
    }
}
