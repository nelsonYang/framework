package com.framework.entity.context;

import com.framework.cache.spi.cache.RollBackCache;
import com.framework.cache.spi.provider.CacheProvider;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.Entity;
import com.framework.entity.threadlocal.RollBackCacheThreadLocalManager;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public final class DAOContext<T extends Entity> {
    
    private static final DAOContext daoContext = new DAOContext();

    public static DAOContext getInstance() {
        return daoContext;
    }
    private final Map<String, EntityDao<T>> entityDaoMap = new HashMap<String, EntityDao<T>>();
    public void putDAO(String entityName,EntityDao<T> entityDao){
        this.entityDaoMap.put(entityName, entityDao);
    }

    public EntityDao<T> getEntityDao(String entityName){
        return this.entityDaoMap.get(entityName);
    }
    private RollBackCacheThreadLocalManager<RollBackCache> threadLocalManager;

    /**
     * @return the threadLocalManager
     */
    public RollBackCacheThreadLocalManager<RollBackCache> getThreadLocalManager() {
        return threadLocalManager;
    }

    /**
     * @param threadLocalManager the threadLocalManager to set
     */
    public void setThreadLocalManager(RollBackCacheThreadLocalManager<RollBackCache> threadLocalManager) {
        this.threadLocalManager = threadLocalManager;
    }
    private CacheProvider cacheProvider;

    /**
     * @param cacheProvider the cacheProvider to set
     */
    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }
    
    public RollBackCache buildRollBackCache(){
        return this.cacheProvider.buildRollBackCache();
    }

}
