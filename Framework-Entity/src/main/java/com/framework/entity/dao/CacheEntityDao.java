package com.framework.entity.dao;

import com.framework.cache.spi.cache.ICache;
import com.framework.cache.spi.cache.RollBackCache;
import com.framework.cache.spi.cachetype.CacheEnum;
import com.framework.cache.spi.key.CacheKey;
import com.framework.cache.spi.value.CacheValue;
import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.context.DAOContext;
import com.framework.entity.jdbc.ConditionUtils;
import com.framework.entity.jdbc.SortUtils;
import com.framework.entity.jdbc.SqlBuilder;
import com.framework.entity.jdbc.SqlExecutor;
import com.framework.entity.pojo.Entity;
import com.framework.entity.pojo.PageModel;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.entity.threadlocal.RollBackCacheThreadLocalManager;
import com.framework.route.entity.RouteInfo;
import com.frameworkLog.factory.LogFactory;
import com.framework.route.provider.RouteProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class CacheEntityDao<T extends Entity> extends AbstractEntityDao<T> {

    private Logger logger = LogFactory.getInstance().getLogger(CacheEntityDao.class);
    private ICache entityCache;
    private ICache sqlCache;
    private Class<CacheKey> cacheKeyClazz;
    private Class<CacheValue> cacheValueClazz;

    public CacheEntityDao(RouteProvider<DataSource> routeProvider, ICache sqlCache, ICache entityCache, Class<CacheKey> cacheKeyClazz, Class<CacheValue> cacheValueClazz, DataSource dataSource, Class<T> clazz, String tableName, String[] fields, String[] keyFields, String countSqlMode, String deleteSqlMode, String inquireSqlMode, String inquireKeyMode, String inquireSqlByKeyMode) {
        this.routeProvider = routeProvider;
        this.entityCache = entityCache;
        this.sqlCache = sqlCache;
        this.cacheKeyClazz = cacheKeyClazz;
        this.cacheValueClazz = cacheValueClazz;
        this.clazz = clazz;
        this.tableName = tableName;
        this.fields = fields;
        this.keyFields = keyFields;
        this.inquireSqlMode = inquireSqlMode;
        this.deleteSqlMode = deleteSqlMode;
        this.inquireKeyMode = inquireKeyMode;
        this.countSqlMode = countSqlMode;
        this.inquireSqlByKeyMode = inquireSqlByKeyMode;
    }

    private CacheKey newCacheKeyInstance() {
        CacheKey cacheKey = null;
        try {
            cacheKey = cacheKeyClazz.newInstance();
        } catch (InstantiationException ex) {
            logger.error("init CacheKeyClass error:" + ex);
            throw new RuntimeException("init CacheKey Error");
        } catch (IllegalAccessException ex) {
            logger.error("init CacheKeyClass error:" + ex);
            throw new RuntimeException("init CacheKey Error");
        }
        return cacheKey;
    }

    private CacheValue newCacheValueInstance() {
        CacheValue cacheValue = null;
        try {
            cacheValue = cacheValueClazz.newInstance();
        } catch (InstantiationException ex) {
            logger.error("init CacheValueClass error:" + ex);
            throw new RuntimeException("init CacheValue Error");
        } catch (IllegalAccessException ex) {
            logger.error("init CacheValueClass error:" + ex);
            throw new RuntimeException("init CacheValue Error");
        }
        return cacheValue;
    }

    private List<PrimaryKey> toPrimaryKeyList(final List<Map<String, String>> resultMapList) {
        List<PrimaryKey> primaryKeyList = new ArrayList<PrimaryKey>(resultMapList.size());
        PrimaryKey primaryKey;
        for (Map<String, String> resultMap : resultMapList) {
            primaryKey = new PrimaryKey();
            primaryKey.setKeyFieldsMap(resultMap);
            primaryKeyList.add(primaryKey);
        }
        return primaryKeyList;

    }

    @Override
    public T insert(Map<String, String> dataMap) {
        DAOContext daoContext = DAOContext.getInstance();
        RollBackCacheThreadLocalManager<RollBackCache> rollBackCacheThreadLocal = daoContext.getThreadLocalManager();
        RollBackCache rollBackCache = rollBackCacheThreadLocal.getValue();
        T t = null;
        List<String> selectFields = new ArrayList<String>(dataMap.size());
        List<String> keyFieldValues = new ArrayList<String>(2);
        List<String> selectFieldValues = new ArrayList<String>(dataMap.size());
        for (String field : fields) {
            if (dataMap.get(field) != null) {
                selectFields.add(field);
                selectFieldValues.add(dataMap.get(field));
            }
        }
        for (String field : this.keyFields) {
            if (keyFields.length == 1) {
                if (dataMap.get(field) != null) {
                    keyFieldValues.add(dataMap.get(field));
                }
            } else {
                if (dataMap.get(field) != null && !dataMap.get(field).isEmpty()) {
                    keyFieldValues.add(dataMap.get(field));
                } else {
                    throw new RuntimeException("key value cannot be null or empty.");
                }
            }
        }
        routeInfo = routeProvider.getRoute(tableName, dataMap);
        // dataSource = routeProvider.getRoute(tableName, dataMap);
        String insertSqlMode = SqlBuilder.getInsertSqlMode(tableName, selectFields.toArray(new String[]{}));
        Map<String, String> resultMap = SqlExecutor.insertAndInquireByKey(routeInfo.getDataSource(), insertSqlMode, selectFieldValues.toArray(new String[]{}), inquireSqlByKeyMode, this.keyFields, keyFieldValues.toArray(new String[]{}));
        t = super.newInstance(resultMap);
        //添加缓存
        PrimaryKey primaryKey = t.getKeyValue();
        CacheKey cacheKey = this.newCacheKeyInstance();
        cacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setTableName(new String[]{tableName}).setPrimaryKey(primaryKey.getKeyValues(keyFields));
        CacheValue entityCacheValue = this.newCacheValueInstance();
        entityCacheValue.put(t.toMap());
        entityCache.putValue(cacheKey, entityCacheValue);
        rollBackCache.putEntityCacheKey(entityCache, cacheKey);
        //清除二级缓存
        sqlCache.removeByTableNames(new String[]{tableName});
        return t;
    }

    @Override
    public boolean delete(PrimaryKey primaryKey) {
        String[] values = primaryKey.getKeyValues(keyFields);
        super.delete(primaryKey);
        //删除缓存
        CacheKey cacheKey = this.newCacheKeyInstance();
        cacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setTableName(new String[]{tableName}).setPrimaryKey(primaryKey.getKeyValues(keyFields));
        entityCache.remove(cacheKey);
        //清除二级缓存
        sqlCache.removeByTableNames(new String[]{tableName});
        return true;
    }

    @Override
    public T update(Map<String, String> dataMap) {
        DAOContext daoContext = DAOContext.getInstance();
        RollBackCacheThreadLocalManager<RollBackCache> rollBackCacheThreadLocal = daoContext.getThreadLocalManager();
        RollBackCache rollBackCache = rollBackCacheThreadLocal.getValue();
        T t = super.update(dataMap);
        //同步缓存
        PrimaryKey primaryKey = t.getKeyValue();
        CacheKey cacheKey = this.newCacheKeyInstance();
        cacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setTableName(new String[]{tableName}).setPrimaryKey(primaryKey.getKeyValues(keyFields));
        CacheValue entityCacheValue = this.newCacheValueInstance();
        entityCacheValue.put(t.toMap());
        entityCache.synchronize(cacheKey, entityCacheValue);
        rollBackCache.putEntityCacheKey(entityCache, cacheKey);
        //清除二级缓存
        sqlCache.removeByTableNames(new String[]{tableName});
        return t;
    }

    @Override
    public List<T> inquireByCondition(List<Condition> conditionList) {
        List<T> tList = new ArrayList<T>(0);
        List<Map<String, String>> resultMapList = null;
        PrimaryKey primaryKey;
        CacheKey entityCacheKey;
        CacheValue entityCacheValue;
        CacheValue sqlCacheValue;
        Map<String, String> entityMap;
        List<PrimaryKey> missPrimaryKeyList = new ArrayList<PrimaryKey>(0);
        String inquireSql = SqlBuilder.inquireByConditionSqlBuilder(tableName, inquireSqlMode, null, conditionList);
        String[] values = SqlBuilder.getConditionValues(conditionList);

        CacheKey sqlCacheKey = this.newCacheKeyInstance();
        sqlCacheKey = sqlCacheKey.setCacheEnum(CacheEnum.SQL_CACHE).setTableName(new String[]{tableName}).setSql(inquireSql).setValues(values);
        CacheValue cacheValue = sqlCache.getValue(sqlCacheKey);
        if (cacheValue != null) {
            resultMapList = cacheValue.getMapList();
            tList = this.newInstance(resultMapList);
        } else {
            String inquireKeySql = SqlBuilder.inquireByKeyConditionSqlBuilder(tableName, this.inquireKeyMode, this.keyFields, conditionList);
            List<Map<String, String>> keyMapList = new ArrayList<Map<String, String>>();
            routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
            Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
            List<Map<String, String>> tempResultMapList;
            for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                tempResultMapList = SqlExecutor.inquireKeys(tempRouteInfo.getDataSource(), inquireKeySql, values);
                keyMapList.addAll(tempResultMapList);
            }
            List<Map<String, String>> cacheMapList = new ArrayList<Map<String, String>>();
            for (Map<String, String> keyMap : keyMapList) {
                CacheKey cacheKey = this.newCacheKeyInstance();
                cacheKey = cacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(keyMap.values().toArray(new String[]{})).setTableName(new String[]{tableName});
                cacheValue = entityCache.getValue(cacheKey);
                if (cacheValue != null) {
                    entityMap = cacheValue.getMap();
                    cacheMapList.add(entityMap);
                } else {
                    primaryKey = new PrimaryKey();
                    primaryKey.setKeyFieldsMap(keyMap);
                    missPrimaryKeyList.add(primaryKey);
                }
            }
            if (!missPrimaryKeyList.isEmpty()) {
                String inquireByKeySql = SqlBuilder.inquireByKeys(tableName, inquireSqlMode, keyFields, missPrimaryKeyList);
                routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
                routeInfoValues = routeInfoMap.values();
                resultMapList = new ArrayList<Map<String, String>>();
                List<Map<String, String>> tempEntityMapList;
                for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                    tempEntityMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), inquireByKeySql);
                    resultMapList.addAll(tempEntityMapList);
                }
                cacheMapList.addAll(resultMapList);
                //sort by key
                SortUtils.sortByResult(this.keyFields, keyMapList, cacheMapList);
                List<T> tResult = this.newInstance(resultMapList);
                //put to EntityCache
                for (T t : tResult) {
                    entityCacheKey = this.newCacheKeyInstance();
                    entityCacheKey = entityCacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(t.getKeyValue().getKeyValues(keyFields)).setTableName(new String[]{tableName});
                    entityCacheValue = this.newCacheValueInstance();
                    Map<String, String> tMap = t.toMap();
                    entityCacheValue.put(tMap);
                    entityCache.putValue(entityCacheKey, entityCacheValue);
                }
              
            }
           tList = this.newInstance(cacheMapList);
            //添加sql缓存
            sqlCacheValue = this.newCacheValueInstance();
            sqlCacheValue.put(cacheMapList);
            sqlCache.putValue(sqlCacheKey, sqlCacheValue);
        }
        return tList;
    }

    @Override
    public List<T> inquireByCondition(List<Condition> conditionList, List<Order> orderList) {
        List<T> tList = new ArrayList<T>(0);
        List<Map<String, String>> resultMapList = null;
        PrimaryKey primaryKey;
        CacheKey entityCacheKey;
        CacheValue entityCacheValue;
        CacheValue sqlCacheValue;
        Map<String, String> entityMap;
        List<PrimaryKey> missPrimaryKeyList = new ArrayList<PrimaryKey>(0);
        String inquireSql = SqlBuilder.inquireByConditionSqlBuilder(tableName, inquireSqlMode, null, conditionList, orderList);
        String[] values = SqlBuilder.getConditionValues(conditionList);

        CacheKey sqlCacheKey = this.newCacheKeyInstance();
        sqlCacheKey = sqlCacheKey.setCacheEnum(CacheEnum.SQL_CACHE).setTableName(new String[]{tableName}).setSql(inquireSql).setValues(values);
        CacheValue cacheValue = sqlCache.getValue(sqlCacheKey);
        if (cacheValue != null) {
            resultMapList = cacheValue.getMapList();
            tList = this.newInstance(resultMapList);
        } else {
            String inquireKeySql = SqlBuilder.inquireByKeyConditionSqlBuilder(tableName, this.inquireKeyMode, this.keyFields, conditionList, orderList);
            List<Map<String, String>> keyMapList = new ArrayList<Map<String, String>>();
            routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
            Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
            List<Map<String, String>> tempResultMapList;
            for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                tempResultMapList = SqlExecutor.inquireKeys(tempRouteInfo.getDataSource(), inquireKeySql, values);
                keyMapList.addAll(tempResultMapList);
            }
            List<Map<String, String>> cacheMapList = new ArrayList<Map<String, String>>();
            for (Map<String, String> keyMap : keyMapList) {
                CacheKey cacheKey = this.newCacheKeyInstance();
                cacheKey = cacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(keyMap.values().toArray(new String[]{})).setTableName(new String[]{tableName});
                cacheValue = entityCache.getValue(cacheKey);
                if (cacheValue != null) {
                    entityMap = cacheValue.getMap();
                    cacheMapList.add(entityMap);
                } else {
                    primaryKey = new PrimaryKey();
                    primaryKey.setKeyFieldsMap(keyMap);
                    missPrimaryKeyList.add(primaryKey);
                }
            }
            if (!missPrimaryKeyList.isEmpty()) {
                String inquireByKeySql = SqlBuilder.inquireByKeys(tableName, inquireSqlMode, keyFields, missPrimaryKeyList);
                routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
                routeInfoValues = routeInfoMap.values();
                resultMapList = new ArrayList<Map<String, String>>();
                List<Map<String, String>> tempEntityMapList;
                for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                    tempEntityMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), inquireByKeySql);
                    resultMapList.addAll(tempEntityMapList);
                }
                cacheMapList.addAll(resultMapList);
                //sort by key
                SortUtils.sortByResult(this.keyFields, keyMapList, cacheMapList);
                List<T> tResult = this.newInstance(resultMapList);
                //put to EntityCache
                for (T t : tResult) {
                    entityCacheKey = this.newCacheKeyInstance();
                    entityCacheKey = entityCacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(t.getKeyValue().getKeyValues(keyFields)).setTableName(new String[]{tableName});
                    entityCacheValue = this.newCacheValueInstance();
                    entityCacheValue.put(t.toMap());
                    entityCache.putValue(entityCacheKey, entityCacheValue);
                }
            
            }
            tList = this.newInstance(cacheMapList);
            //添加sql缓存
            sqlCacheValue = this.newCacheValueInstance();
            sqlCacheValue.put(cacheMapList);
            sqlCache.putValue(sqlCacheKey, sqlCacheValue);
        }

        return tList;

    }

    @Override
    public PageModel inquirePageByCondition(List<Condition> conditionList, int start, int rows) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(start);
        pageModel.setPageCount(rows);
        List<T> tList = new ArrayList<T>(0);
        List<Map<String, String>> resultMapList = null;
        PrimaryKey primaryKey;
        CacheKey entityCacheKey;
        CacheValue entityCacheValue;
        CacheValue sqlCacheValue;
        Map<String, String> entityMap;
        List<PrimaryKey> missPrimaryKeyList = new ArrayList<PrimaryKey>(0);
        String inquirePageSql = SqlBuilder.inquirePageByConditionSqlBuilder(tableName, inquireSqlMode, null, conditionList, pageModel.getStartIndex(), rows);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        CacheKey sqlCacheKey = this.newCacheKeyInstance();
        sqlCacheKey = sqlCacheKey.setCacheEnum(CacheEnum.SQL_CACHE).setTableName(new String[]{tableName}).setSql(inquirePageSql).setValues(values);
        CacheValue cacheValue = sqlCache.getValue(sqlCacheKey);
        if (cacheValue != null) {
            resultMapList = cacheValue.getMapList();
            tList = this.newInstance(resultMapList);
        } else {
            String inquireKeySql = SqlBuilder.inquirePageKeyByConditionSqlBuilder(tableName, this.inquireKeyMode, this.keyFields, conditionList, pageModel.getStartIndex(), rows);
            List<Map<String, String>> keyMapList = new ArrayList<Map<String, String>>();
            routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
            Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
            List<Map<String, String>> tempResultMapList;
            for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                tempResultMapList = SqlExecutor.inquireKeys(tempRouteInfo.getDataSource(), inquireKeySql, values);
                keyMapList.addAll(tempResultMapList);
            }
            List<Map<String, String>> cacheMapList = new ArrayList<Map<String, String>>();
            for (Map<String, String> keyMap : keyMapList) {
                CacheKey cacheKey = this.newCacheKeyInstance();
                cacheKey = cacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(keyMap.values().toArray(new String[]{})).setTableName(new String[]{tableName});
                cacheValue = entityCache.getValue(cacheKey);
                if (cacheValue != null) {
                    entityMap = cacheValue.getMap();
                    cacheMapList.add(entityMap);
                } else {
                    primaryKey = new PrimaryKey();
                    primaryKey.setKeyFieldsMap(keyMap);
                    missPrimaryKeyList.add(primaryKey);
                }
            }
            if (!missPrimaryKeyList.isEmpty()) {
                String inquireByKeySql = SqlBuilder.inquireByKeys(tableName, inquireSqlMode, keyFields, missPrimaryKeyList);
                routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
                routeInfoValues = routeInfoMap.values();
                resultMapList = new ArrayList<Map<String, String>>();
                List<Map<String, String>> tempEntityMapList;
                for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                    tempEntityMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), inquireByKeySql);
                    resultMapList.addAll(tempEntityMapList);
                }
                cacheMapList.addAll(resultMapList);
                //sort by key
                SortUtils.sortByResult(this.keyFields, keyMapList, cacheMapList);
                List<T> tResult = this.newInstance(resultMapList);
                //put to EntityCache
                for (T t : tResult) {
                    entityCacheKey = this.newCacheKeyInstance();
                    entityCacheKey = entityCacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(t.getKeyValue().getKeyValues(keyFields)).setTableName(new String[]{tableName});
                    entityCacheValue = this.newCacheValueInstance();
                    entityCacheValue.put(t.toMap());
                    entityCache.putValue(entityCacheKey, entityCacheValue);
                }
            }
            tList = this.newInstance(cacheMapList);
            //添加sql缓存
            sqlCacheValue = this.newCacheValueInstance();
            sqlCacheValue.put(cacheMapList);
            sqlCache.putValue(sqlCacheKey, sqlCacheValue);
        }
        int totalCount = this.countByCondition(conditionList);

        pageModel.setTotalCount(totalCount);
        pageModel.setDataList(tList);
        return pageModel;
    }

    @Override
    public PageModel inquirePageByCondition(List<Condition> conditionList, int start, int rows, List<Order> orderList) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(start);
        pageModel.setPageCount(rows);
        List<T> tList = new ArrayList<T>(0);
        List<Map<String, String>> resultMapList = null;
        PrimaryKey primaryKey;
        CacheKey entityCacheKey;
        CacheValue entityCacheValue;
        CacheValue sqlCacheValue;
        Map<String, String> entityMap;
        List<PrimaryKey> missPrimaryKeyList = new ArrayList<PrimaryKey>(0);
        String inquirePageSql = SqlBuilder.inquirePageByConditionSqlBuilder(tableName, inquireSqlMode, null, conditionList, orderList, pageModel.getStartIndex(), rows);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        CacheKey sqlCacheKey = this.newCacheKeyInstance();
        sqlCacheKey = sqlCacheKey.setCacheEnum(CacheEnum.SQL_CACHE).setTableName(new String[]{tableName}).setSql(inquirePageSql).setValues(values);
        CacheValue cacheValue = sqlCache.getValue(sqlCacheKey);
        if (cacheValue != null) {
            resultMapList = cacheValue.getMapList();
            tList = this.newInstance(resultMapList);
        } else {
            String inquireKeySql = SqlBuilder.inquirePageKeyByConditionSqlBuilder(tableName, this.inquireKeyMode, this.keyFields, conditionList, orderList, pageModel.getStartIndex(), rows);
            List<Map<String, String>> keyMapList = new ArrayList<Map<String, String>>();
            routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
            Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
            List<Map<String, String>> tempResultMapList;
            for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                tempResultMapList = SqlExecutor.inquireKeys(tempRouteInfo.getDataSource(), inquireKeySql, values);
                keyMapList.addAll(tempResultMapList);
            }
            List<Map<String, String>> cacheMapList = new ArrayList<Map<String, String>>();
            for (Map<String, String> keyMap : keyMapList) {
                CacheKey cacheKey = this.newCacheKeyInstance();
                cacheKey = cacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(keyMap.values().toArray(new String[]{})).setTableName(new String[]{tableName});
                cacheValue = entityCache.getValue(cacheKey);
                if (cacheValue != null) {
                    entityMap = cacheValue.getMap();
                    cacheMapList.add(entityMap);
                } else {
                    primaryKey = new PrimaryKey();
                    primaryKey.setKeyFieldsMap(keyMap);
                    missPrimaryKeyList.add(primaryKey);
                }
            }
            if (!missPrimaryKeyList.isEmpty()) {
                String inquireByKeySql = SqlBuilder.inquireByKeys(tableName, inquireSqlMode, keyFields, missPrimaryKeyList);
                routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
                routeInfoValues = routeInfoMap.values();
                resultMapList = new ArrayList<Map<String, String>>();
                List<Map<String, String>> tempEntityMapList;
                for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                    tempEntityMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), inquireByKeySql);
                    resultMapList.addAll(tempEntityMapList);
                }
                cacheMapList.addAll(resultMapList);
                SortUtils.sortByResult(keyFields, keyMapList, cacheMapList);
                List<T> tResult = this.newInstance(resultMapList);
                //put to EntityCache
                for (T t : tResult) {
                    entityCacheKey = this.newCacheKeyInstance();
                    entityCacheKey = entityCacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(t.getKeyValue().getKeyValues(keyFields)).setTableName(new String[]{tableName});
                    entityCacheValue = this.newCacheValueInstance();
                    entityCacheValue.put(t.toMap());
                    entityCache.putValue(entityCacheKey, entityCacheValue);
                }
            }
             tList = this.newInstance(cacheMapList);
            //添加sql缓存
            sqlCacheValue = this.newCacheValueInstance();
            sqlCacheValue.put(cacheMapList);
            sqlCache.putValue(sqlCacheKey, sqlCacheValue);
        }
        int totalCount = this.countByCondition(conditionList);

        pageModel.setTotalCount(totalCount);
        pageModel.setDataList(tList);
        return pageModel;
    }

    @Override
    public int countByCondition(List<Condition> conditionList) {
        int count = 0;
        String countSql = SqlBuilder.countByConditionSqlBuilder(tableName, countSqlMode, conditionList);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        CacheKey cacheKey = this.newCacheKeyInstance();
        cacheKey = cacheKey.setCacheEnum(CacheEnum.SQL_CACHE).setTableName(new String[]{tableName}).setSql(countSql).setValues(values);
        CacheValue cacheValue = sqlCache.getValue(cacheKey);
        if (cacheValue != null) {
            List<Map<String, String>> resultMapList = cacheValue.getMapList();
            count = Integer.parseInt(resultMapList.get(0).get("count"));
        } else {
            routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
            Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
            int tempCount = 0;
            for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                tempCount = SqlExecutor.count(tempRouteInfo.getDataSource(), countSql, values);
                count = count + tempCount;
            }
            cacheValue = this.newCacheValueInstance();
            List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(1);
            Map<String, String> resultMap = new HashMap<String, String>(2, 1);
            resultMap.put("count", String.valueOf(count));
            resultMapList.add(resultMap);
            cacheValue.put(resultMapList);
            sqlCache.putValue(cacheKey, cacheValue);
        }
        return count;
    }

    @Override
    public List<Map<String, String>> executeQueryBySql(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
        CacheKey cacheKey = this.newCacheKeyInstance();
        cacheKey = cacheKey.setCacheEnum(CacheEnum.SQL_CACHE).setTableName(tableNames).setSql(sql).setValues(values);
        CacheValue cacheValue = sqlCache.getValue(cacheKey);
        if (cacheValue != null) {
            return cacheValue.getMapList();
        } else {
            routeInfoMap = routeProvider.getRoutes(tableName, dataMap);
            Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
            List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            List<Map<String, String>> tempResultMapList;
            for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
                tempResultMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), sql, values);
                mapList.addAll(tempResultMapList);
            }
            cacheValue = this.newCacheValueInstance();
            cacheValue.put(mapList);
            sqlCache.putValue(cacheKey, cacheValue);
            return mapList;
        }

    }

    @Override
    public List<PrimaryKey> inquireKeyByCondition(List<Condition> conditionList) {
        String inquireKeySql = SqlBuilder.inquireByKeyConditionSqlBuilder(tableName, inquireKeyMode, keyFields, conditionList);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        List<Map<String, String>> tempEntityMapList;
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            tempEntityMapList = SqlExecutor.inquireKeys(tempRouteInfo.getDataSource(), inquireKeySql, values);
            resultMapList.addAll(tempEntityMapList);
        }
        return this.toPrimaryKeyList(resultMapList);
    }

    @Override
    public T inqurieByKey(PrimaryKey primaryKey) {
        T t = null;
        Map<String, String> entityMap;
        String[] values = primaryKey.getKeyValues(keyFields);
        CacheKey sqlCacheKey = this.newCacheKeyInstance();
        sqlCacheKey = sqlCacheKey.setCacheEnum(CacheEnum.SQL_CACHE).setTableName(new String[]{tableName}).setSql(this.inquireSqlByKeyMode).setValues(values);
        CacheValue cacheValue = sqlCache.getValue(sqlCacheKey);
        if (cacheValue != null) {
            Map<String, String> resultMap = (Map<String, String>) cacheValue.getMapList().get(0);
            t = this.newInstance(resultMap);
        } else {
            CacheKey cacheKey = this.newCacheKeyInstance();
            cacheKey = cacheKey.setCacheEnum(CacheEnum.ENTITY_CACHE).setPrimaryKey(values).setTableName(new String[]{tableName});
            cacheValue = entityCache.getValue(cacheKey);
            if (cacheValue != null) {
                entityMap = cacheValue.getMap();
                t = this.newInstance(entityMap);
            } else {
                routeInfo = routeProvider.getRoute(tableName, primaryKey.getKeyFieldsMap());
                Map<String, String> resultMap = SqlExecutor.inquireByKey(routeInfo.getDataSource(), inquireSqlByKeyMode, values);
                if (resultMap != null) {
                    t = this.newInstance(resultMap);
                    cacheValue = this.newCacheValueInstance();
                    cacheValue.put(t.toMap());
                    entityCache.putValue(cacheKey, cacheValue);
                }
            }
        }
        return t;
    }

    @Override
    public boolean executeUpdateBySql(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
        boolean isSuccess = super.executeUpdateBySql(tableNames, sql, values, dataMap);
        if (isSuccess) {
            sqlCache.removeByTableNames(new String[]{tableName});
            entityCache.removeByCacheNames(tableNames);
        }
        return isSuccess;
    }

    public int executeUpdateBySqlAndTakeRows(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
        int rows = super.executeUpdateBySqlAndTakeRows(tableNames, sql, values, dataMap);
        if (-1 != rows) {
            sqlCache.removeByTableNames(new String[]{tableName});
            entityCache.removeByCacheNames(tableNames);
        }
        return rows;
    }
}
