package com.framework.entity.builder;

import com.framework.cache.spi.cache.ICache;
import com.framework.cache.spi.cache.RollBackCache;
import com.framework.cache.spi.config.CacheConfig;
import com.framework.cache.spi.key.CacheKey;
import com.framework.cache.spi.provider.CacheProvider;
import com.framework.cache.spi.value.CacheValue;
import com.framework.entity.annotation.EntityConfig;
import com.framework.entity.configuration.ProviderConfigurationManager;
import com.framework.entity.context.DAOContext;
import com.framework.entity.dao.CacheEntityDao;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.dao.NoCachceEntityDao;
import com.framework.entity.handler.EntityHandler;
import com.framework.entity.handler.FieldHandler;
import com.framework.entity.jdbc.SqlBuilder;
import com.framework.entity.parser.ClassParser;
import com.framework.entity.parser.EntityConfigParser;
import com.framework.entity.parser.ExtendedEntityConfigParser;
import com.framework.entity.pojo.Entity;
import com.framework.entity.threadlocal.RollBackCacheThreadLocalManager;
import com.framework.route.provider.RouteProvider;
import com.frameworkLog.factory.LogFactory;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public final class DAOContextBuilder<T extends Entity> {

    private static final Logger logger = LogFactory.getInstance().getLogger(DAOContextBuilder.class);
    private EntityContextBuilder entityContextBuilder;
    private ExtendedEntityContextBuilder extendedEntityContextBuilder;
    private ProviderConfigurationManager providerConfigurationManager;
    private RollBackCacheThreadLocalManager<RollBackCache> threadLocalManager;
    private String[] packages;
    private DataSource dataSource;

    public DAOContext build() {
        try {
            DAOContext daoContext = DAOContext.getInstance();
            List<String> classNameList = new ClassParser().parse(this.packages);
            EntityConfigParser<T> entityConfigParser = new EntityConfigParser<T>();
            entityConfigParser.parse(classNameList, entityContextBuilder);
            ExtendedEntityConfigParser extendedEntityConfigParser = new ExtendedEntityConfigParser();
            extendedEntityConfigParser.parse(classNameList, extendedEntityContextBuilder);
            Map<String, EntityHandler> entityHandlerMap = entityContextBuilder.getEntityHandlerMap();
            EntityHandler entityHandler;
            EntityConfig entityConfig;
            String tableName;
            EntityDao entityDao;
            Class<T> clazz;
            String[] keyFields;
            String inquireKeySqlMode;
            String inquireSqlMode;
            String deleteSqlMode;
            String countSqlMode;
            String inquireSqlByKeyMode;
            String[] selectFields;
            CacheConfig entityCacheConfig;
            ICache entityCache;
            Map<String, FieldHandler> fieldHandlerMap;
            providerConfigurationManager.readConfiguration("provider.properties");
            String cacheKeyClassName = providerConfigurationManager.getCacheKeyClassName();
            String cacheValueClassName = providerConfigurationManager.getCacheValueClassName();
            String cacheProviderClassName = providerConfigurationManager.getCacheProviderClassName();
            String rollbackCacheClassName = providerConfigurationManager.getRollbackCacheClassName();
            String dataSourceRouteProviderClassName = providerConfigurationManager.getDataSourceRouteProviderClassName();
            Class<CacheKey> cacheKeyClazz = (Class<CacheKey>) Class.forName(cacheKeyClassName);
            Class<CacheValue> cacheValueClazz = (Class<CacheValue>) Class.forName(cacheValueClassName);
            Class<CacheProvider> cacheProviderClazz = (Class<CacheProvider>) Class.forName(cacheProviderClassName);
            Class<RouteProvider> dataSourceRouteProviderClazz = (Class<RouteProvider>) Class.forName(dataSourceRouteProviderClassName);
            Class<RollBackCache> rollBackCacheClazz = (Class<RollBackCache>) Class.forName(rollbackCacheClassName);
            CacheProvider cacheProvider = cacheProviderClazz.newInstance();
            RouteProvider<DataSource> routeProvider = dataSourceRouteProviderClazz.newInstance();
            //注册路由
            routeProvider.registerRoutes();
            //注册sql缓存
            CacheConfig sqlCacheConfig = new CacheConfig();
            sqlCacheConfig.setMaxElementsInMemory(50000);
            sqlCacheConfig.setTimeToIdleSeconds(6000);
            sqlCacheConfig.setTimeToLiveSeconds(600000);
            sqlCacheConfig.setTableName("sql-Cache");
            ICache sqlCache = cacheProvider.buildCache("yinpiao_sql_Cache", sqlCacheConfig);
            //设置cacheprovider
            cacheProvider.setRollbackCacheClazz(rollBackCacheClazz);
            daoContext.setCacheProvider(cacheProvider);
            daoContext.setThreadLocalManager(threadLocalManager);
            boolean useCache;
            for (Entry<String, EntityHandler> entry : entityHandlerMap.entrySet()) {
                entityHandler = entry.getValue();
                entityConfig = entityHandler.getEntityConfig();
                useCache = entityConfig.useCache();
                logger.debug("useCache:{}", useCache);
                tableName = entityConfig.entityName();
                logger.debug("tableName:{}", tableName);
                keyFields = entityConfig.keyFields();
                clazz = entityHandler.getClazz();
                fieldHandlerMap = entityHandler.getFieldHandlerMap();
                selectFields = fieldHandlerMap.keySet().toArray(new String[]{});
                deleteSqlMode = SqlBuilder.getDeleteSqlMode(tableName, keyFields);
                inquireSqlMode = SqlBuilder.getInquireSqlMode(tableName, selectFields);
                inquireKeySqlMode = SqlBuilder.getInquireKeySqlMode(tableName, keyFields);
                countSqlMode = SqlBuilder.getCountSqlMode(tableName);
                inquireSqlByKeyMode = SqlBuilder.getInquireByKeySqlMode(tableName, selectFields, keyFields);
                if (useCache) {
                    entityCacheConfig = new CacheConfig();
                    entityCacheConfig.setMaxElementsInMemory(entityConfig.maxElementsInMemory());
                    entityCacheConfig.setTableName(tableName);
                    entityCacheConfig.setTimeToIdleSeconds(entityConfig.timeToIdleSeconds());
                    entityCacheConfig.setTimeToLiveSeconds(entityConfig.timeToLiveSeconds());
                    entityCache = cacheProvider.buildCache(tableName, entityCacheConfig);
                    entityDao = new CacheEntityDao(routeProvider, sqlCache, entityCache, cacheKeyClazz, cacheValueClazz, getDataSource(), clazz, tableName, selectFields, keyFields, countSqlMode, deleteSqlMode, inquireSqlMode, inquireKeySqlMode, inquireSqlByKeyMode);
                } else {
                    entityDao = new NoCachceEntityDao(routeProvider, getDataSource(), clazz, tableName, selectFields, keyFields, countSqlMode, deleteSqlMode, inquireSqlMode, inquireKeySqlMode, inquireSqlByKeyMode);
                }
                daoContext.putDAO(tableName, entityDao);
            }
            return daoContext;
        } catch (Exception ex) {
            //log it
            ex.printStackTrace();
            logger.error("构造DAOContext出错", ex);
            throw new RuntimeException("构造DAOContext出错");
        }

    }

    /**
     * @param entityContextBuilder the entityContextBuilder to set
     */
    public DAOContextBuilder setEntityContextBuilder(EntityContextBuilder entityContextBuilder) {
        this.entityContextBuilder = entityContextBuilder;
        return this;
    }

    /**
     * @param extendedEntityContextBuilder the extendedEntityContextBuilder to
     * set
     */
    public DAOContextBuilder setExtendedEntityContextBuilder(ExtendedEntityContextBuilder extendedEntityContextBuilder) {
        this.extendedEntityContextBuilder = extendedEntityContextBuilder;
        return this;
    }

    /**
     * @param packages the packages to set
     */
    public DAOContextBuilder setPackages(String[] packages) {
        this.packages = packages;
        return this;
    }

    /**
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public DAOContextBuilder setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    /**
     * @param providerConfigurationManager the providerConfigurationManager to
     * set
     */
    public DAOContextBuilder setProviderConfigurationManager(ProviderConfigurationManager providerConfigurationManager) {
        this.providerConfigurationManager = providerConfigurationManager;
        return this;
    }

    /**
     * @param threadLocalManager the threadLocalManager to set
     */
    public DAOContextBuilder setThreadLocalManager(RollBackCacheThreadLocalManager<RollBackCache> threadLocalManager) {
        this.threadLocalManager = threadLocalManager;
        return this;
    }
}
