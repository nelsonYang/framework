package com.framework.cache.spi.config;

import java.util.Properties;

/**
 *
 * @author nelson
 */
public class CacheConfig {

    private Properties properties;
    private String tableName;
    private long maxElementsInMemory;
    private long timeToIdleSeconds;
    private long timeToLiveSeconds;

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the maxElementsInMemory
     */
    public long getMaxElementsInMemory() {
        return maxElementsInMemory;
    }

    /**
     * @param maxElementsInMemory the maxElementsInMemory to set
     */
    public void setMaxElementsInMemory(long maxElementsInMemory) {
        this.maxElementsInMemory = maxElementsInMemory;
    }

    /**
     * @return the timeToIdleSeconds
     */
    public long getTimeToIdleSeconds() {
        return timeToIdleSeconds;
    }

    /**
     * @param timeToIdleSeconds the timeToIdleSeconds to set
     */
    public void setTimeToIdleSeconds(long timeToIdleSeconds) {
        this.timeToIdleSeconds = timeToIdleSeconds;
    }

    /**
     * @return the timeToLiveSeconds
     */
    public long getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    /**
     * @param timeToLiveSeconds the timeToLiveSeconds to set
     */
    public void setTimeToLiveSeconds(long timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }
}
