package com.framework.entity.dao;

import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.jdbc.ConditionUtils;
import com.framework.entity.jdbc.SqlBuilder;
import com.framework.entity.jdbc.SqlExecutor;
import com.framework.entity.pojo.Entity;
import com.framework.entity.pojo.PageModel;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.route.entity.RouteInfo;
import com.framework.route.provider.RouteProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;

/**
 *
 * @author nelson
 */
public class AbstractEntityDao<T extends Entity> implements EntityDao<T> {

    protected String tableName;
    protected String deleteSqlMode;
    protected String inquireSqlMode;
    protected String inquireKeyMode;
    protected String countSqlMode;
    protected String inquireSqlByKeyMode;
    protected String[] keyFields;
    protected RouteInfo<DataSource> routeInfo;
    protected Map<String, RouteInfo<DataSource>> routeInfoMap;
    protected String[] fields;
    protected Class<T> clazz;
    protected RouteProvider<DataSource> routeProvider;

    protected T newInstance(final Map<String, String> resultMap) {
        T t = null;
        try {
            t = clazz.newInstance();
            t.parseMap(resultMap);
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return t;
    }

    protected List<T> newInstance(final List<Map<String, String>> resultMapList) {
        List<T> tList = new ArrayList<T>(resultMapList.size());
        T t;
        for (Map<String, String> resultMap : resultMapList) {
            try {
                t = clazz.newInstance();
                t.parseMap(resultMap);
                tList.add(t);
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        return tList;
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

    public T insert(Map<String, String> dataMap) {
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
        String insertSqlMode = SqlBuilder.getInsertSqlMode(tableName, selectFields.toArray(new String[]{}));
        Map<String, String> resultMap = SqlExecutor.insertAndInquireByKey(routeInfo.getDataSource(), insertSqlMode, selectFieldValues.toArray(new String[]{}), inquireSqlByKeyMode, this.keyFields, keyFieldValues.toArray(new String[]{}));
        t = this.newInstance(resultMap);
        return t;
    }

    public boolean delete(PrimaryKey primaryKey) {
        String[] values = primaryKey.getKeyValues(keyFields);
        routeInfoMap = routeProvider.getRoutes(tableName, primaryKey.getKeyFieldsMap());
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            SqlExecutor.delete(tempRouteInfo.getDataSource(), deleteSqlMode, values);
        }
        return true;
    }

    public T update(Map<String, String> dataMap) {
        T t = null;
        String value;
        boolean isValidate = true;
        List<String> keyValueList = new ArrayList<String>();
        for (String keyField : this.keyFields) {
            value = dataMap.get(keyField);
            if (value == null || value.isEmpty()) {
                isValidate = false;
                break;
            } else {
                keyValueList.add(value);
            }
        }
        if (isValidate) {
            Set<String> fieldNames = dataMap.keySet();
            List<String> validateFieldNames = new ArrayList<String>();
            List<String> fieldValues = new ArrayList<String>();
            for (String field : fields) {
                if (fieldNames.contains(field)) {
                    validateFieldNames.add(field);
                    fieldValues.add(dataMap.get(field));
                }
            }
            for (String fieldValue : keyValueList) {
                fieldValues.add(fieldValue);
            }
            String[] validateFields = validateFieldNames.toArray(new String[]{});
            String updateSql = SqlBuilder.getUpdateSqlMode(tableName, validateFields, keyFields);
            routeInfo = routeProvider.getRoute(tableName, dataMap);
            Map<String, String> resultMap = SqlExecutor.updateAndInquireByKey(routeInfo.getDataSource(), updateSql, fieldValues.toArray(new String[]{}), inquireSqlByKeyMode, keyValueList.toArray(new String[]{}));
            t = this.newInstance(resultMap);
            return t;
        } else {
            throw new RuntimeException(" primary key is empty.");
        }
    }

    public List<T> inquireByCondition(List<Condition> conditionList) {
        String inquireSql = SqlBuilder.inquireByConditionSqlBuilder(tableName, inquireSqlMode, null, conditionList);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(0);
        List<Map<String, String>> tempResultMapList;
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            tempResultMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), inquireSql, values);
            resultMapList.addAll(tempResultMapList);
        }
        List<T> tList = this.newInstance(resultMapList);
        return tList;
    }

    public List<T> inquireByCondition(List<Condition> conditionList, List<Order> orderList) {
        String inquireSql = SqlBuilder.inquireByConditionSqlBuilder(tableName, inquireSqlMode, null, conditionList, orderList);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(0);
        List<Map<String, String>> tempResultMapList;
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            tempResultMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), inquireSql, values);
            resultMapList.addAll(tempResultMapList);
        }
        List<T> tList = this.newInstance(resultMapList);
        return tList;

    }

    public PageModel inquirePageByCondition(List<Condition> conditionList, int start, int rows) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(start);
        pageModel.setPageCount(rows);
        String inquirePageSql = SqlBuilder.inquirePageByConditionSqlBuilder(tableName, inquireSqlMode, null, conditionList, pageModel.getStartIndex(), rows);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        int totalCount = this.countByCondition(conditionList);
        routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(0);
        List<Map<String, String>> tempResultMapList;
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            tempResultMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), inquirePageSql, values);
            resultMapList.addAll(tempResultMapList);
        }
        List<T> tList = this.newInstance(resultMapList);
        pageModel.setTotalCount(totalCount);
        pageModel.setDataList(tList);
        return pageModel;
    }

    public PageModel inquirePageByCondition(List<Condition> conditionList, int start, int rows, List<Order> orderList) {
        PageModel pageModel = new PageModel();
        pageModel.setPageNo(start);
        pageModel.setPageCount(rows);
        String inquirePageSql = SqlBuilder.inquirePageByConditionSqlBuilder(tableName, inquireSqlMode, null, conditionList, orderList, pageModel.getStartIndex(), rows);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        int totalCount = this.countByCondition(conditionList);
        routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(0);
        List<Map<String, String>> tempResultMapList;
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            tempResultMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), inquirePageSql, values);
            resultMapList.addAll(tempResultMapList);
        }
        List<T> tList = this.newInstance(resultMapList);
        pageModel.setTotalCount(totalCount);
        pageModel.setDataList(tList);
        return pageModel;
    }

    public int countByCondition(List<Condition> conditionList) {
        String countSql = SqlBuilder.countByConditionSqlBuilder(tableName, countSqlMode, conditionList);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        int count = 0;
        routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            int tempCount = SqlExecutor.count(tempRouteInfo.getDataSource(), countSql, values);
            count = count + tempCount;
        }
        return count;
    }

    public List<PrimaryKey> inquireKeyByCondition(List<Condition> conditionList) {
        String inquireKeySql = SqlBuilder.inquireByKeyConditionSqlBuilder(tableName, inquireKeyMode, keyFields, conditionList);
        String[] values = SqlBuilder.getConditionValues(conditionList);
        routeInfoMap = routeProvider.getRoutes(tableName, ConditionUtils.getConditonMap(conditionList));
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(0);
        List<Map<String, String>> tempResultMapList;
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            tempResultMapList = SqlExecutor.inquireKeys(tempRouteInfo.getDataSource(), inquireKeySql, values);
            resultMapList.addAll(tempResultMapList);
        }
        return this.toPrimaryKeyList(resultMapList);
    }

    public List<Map<String, String>> inquireBySql(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
        routeInfoMap = routeProvider.getRoutes(tableName, dataMap);
        Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
        List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(0);
        List<Map<String, String>> tempResultMapList;
        for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
            tempResultMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), sql, values);
            resultMapList.addAll(tempResultMapList);
        }
        return resultMapList;
    }

    public String seqValue(String tableName) {
        routeInfo = routeProvider.getRoute(tableName, new HashMap<String, String>(2, 1));
        String key = SqlExecutor.seqValue(routeInfo.getDataSource(), tableName);
        return key;
    }

    public T inqurieByKey(PrimaryKey primaryKey) {
        routeInfo = routeProvider.getRoute(tableName, primaryKey.getKeyFieldsMap());
        String[] values = primaryKey.getKeyValues(keyFields);
        Map<String, String> resultMap = SqlExecutor.inquireByKey(routeInfo.getDataSource(), inquireSqlByKeyMode, values);
         T t = null;
        if(resultMap != null){
            t = this.newInstance(resultMap);
        }
        return t;
    }

    public boolean executeUpdateBySql(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
         routeInfo = routeProvider.getRoute(tableName, dataMap);
        return SqlExecutor.update(routeInfo.getDataSource(), sql, values);
    }
    
    public int executeUpdateBySqlAndTakeRows(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
        routeInfo = routeProvider.getRoute(tableName, dataMap);
       return SqlExecutor.updateAndTakeRows(routeInfo.getDataSource(), sql, values);
   }
    
     public List<Map<String, String>> executeQueryBySql(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
         routeInfoMap = routeProvider.getRoutes(tableName, dataMap);
         Collection<RouteInfo<DataSource>> routeInfoValues = routeInfoMap.values();
         List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(0);
         List<Map<String, String>> tempResultMapList;
         for (RouteInfo<DataSource> tempRouteInfo : routeInfoValues) {
             tempResultMapList = SqlExecutor.inquireList(tempRouteInfo.getDataSource(), sql, values);
             resultMapList.addAll(tempResultMapList);
         }
         return resultMapList;
    }

    public List<T> inquireNoCacheByCondition(List<Condition> conditionList) {
        return this.inquireByCondition(conditionList);
    }

    public List<T> inquireNoCacheByCondition(List<Condition> conditionList, List<Order> orderList) {
        return this.inquireByCondition(conditionList, orderList);
    }

    public PageModel inquirePageNoCacheByCondition(List<Condition> conditionList, int start, int rows) {
        return this.inquirePageByCondition(conditionList, start, rows);
    }

    public PageModel inquirePageNoCacheByCondition(List<Condition> conditionList, int start, int rows, List<Order> orderList) {
        return this.inquirePageByCondition(conditionList, start, rows, orderList);
    }

    public int countNoCacheByCondition(List<Condition> conditionList) {
        return this.countByCondition(conditionList);
    }

    public List<PrimaryKey> inquireNoCacheKeyByCondition(List<Condition> conditionList) {
        return this.inquireKeyByCondition(conditionList);
    }

//    public List<Map<String,String>> inquireNoCacheBySql(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
//        return this.inquireBySql(tableNames, sql, values, dataMap);
//    }

    public List<Map<String, String>> executeQueryNoCacheBySql(String[] tableNames, String sql, String[] values, Map<String, String> dataMap) {
        return this.executeQueryBySql(tableNames, sql, values, dataMap);
    }

    public T inqurieNoCacheByKey(PrimaryKey primaryKey) {
        return this.inqurieByKey(primaryKey);
    }
}
