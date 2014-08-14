package com.framework.entity.configuration;

import com.frameworkLog.factory.LogFactory;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class ProviderConfigurationManager {
    private static final Logger logger = LogFactory.getInstance().getLogger(ProviderConfigurationManager.class);
    private static final ProviderConfigurationManager instance = new ProviderConfigurationManager();
    private String cacheKeyClassName;
    private String cacheValueClassName;
    private String rollbackCacheClassName;
    private String cacheProviderClassName;
    private String dataSourceRouteProviderClassName;

    public static ProviderConfigurationManager getInstance() {
        return instance;
    }
    //

    public void readConfiguration(String fileName) {
        InputStream is = this.getClass().getResourceAsStream("/" + fileName);
        Properties property = new Properties();
        try {
            property.load(is);
            this.setCacheKeyClassName(property.getProperty("cacheKey"));
            this.setCacheValueClassName(property.getProperty("cacheValue"));
            this.setCacheProviderClassName(property.getProperty("cacheProvider"));
            this.setDataSourceRouteProviderClassName(property.getProperty("dataSourceRouteProvider"));
            this.setRollbackCacheClassName(property.getProperty("rollbackCache"));
        } catch (Exception ex) {
            logger.error("读取配置文件出错:{}",ex);
            throw new RuntimeException("读取配置文件出错");
        }
    }

    /**
     * @return the cacheKeyClassName
     */
    public String getCacheKeyClassName() {
        return cacheKeyClassName;
    }

    /**
     * @param cacheKeyClassName the cacheKeyClassName to set
     */
    public void setCacheKeyClassName(String cacheKeyClassName) {
        this.cacheKeyClassName = cacheKeyClassName;
    }

    /**
     * @return the cacheValueClassName
     */
    public String getCacheValueClassName() {
        return cacheValueClassName;
    }

    /**
     * @param cacheValueClassName the cacheValueClassName to set
     */
    public void setCacheValueClassName(String cacheValueClassName) {
        this.cacheValueClassName = cacheValueClassName;
    }

    /**
     * @return the cacheProviderClassName
     */
    public String getCacheProviderClassName() {
        return cacheProviderClassName;
    }

    /**
     * @param cacheProviderClassName the cacheProviderClassName to set
     */
    public void setCacheProviderClassName(String cacheProviderClassName) {
        this.cacheProviderClassName = cacheProviderClassName;
    }

    /**
     * @return the dataSourceRouteProviderClassName
     */
    public String getDataSourceRouteProviderClassName() {
        return dataSourceRouteProviderClassName;
    }

    /**
     * @param dataSourceRouteProviderClassName the
     * dataSourceRouteProviderClassName to set
     */
    public void setDataSourceRouteProviderClassName(String dataSourceRouteProviderClassName) {
        this.dataSourceRouteProviderClassName = dataSourceRouteProviderClassName;
    }

    /**
     * @return the rollbackCacheClassName
     */
    public String getRollbackCacheClassName() {
        return rollbackCacheClassName;
    }

    /**
     * @param rollbackCacheClassName the rollbackCacheClassName to set
     */
    public void setRollbackCacheClassName(String rollbackCacheClassName) {
        this.rollbackCacheClassName = rollbackCacheClassName;
    }
}
