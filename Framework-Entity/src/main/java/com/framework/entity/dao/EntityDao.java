package com.framework.entity.dao;

import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.pojo.Entity;
import com.framework.entity.pojo.PageModel;
import com.framework.entity.pojo.PrimaryKey;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface EntityDao<T extends Entity> {
    public T insert(Map<String,String> dataMap);
    
    public boolean delete(PrimaryKey primaryKey);
    
    public T update(Map<String,String> dataMap);
    
    public List<T> inquireByCondition(List<Condition> conditionList);
    
    public List<T> inquireByCondition(List<Condition> conditionList,List<Order> orderList);
    
    public PageModel inquirePageByCondition(List<Condition> conditionList,int start, int rows);
    
    public PageModel inquirePageByCondition(List<Condition> conditionList,int start, int rows,List<Order> orderList);
    
    public int countByCondition(List<Condition> conditionList);
    
    public List<PrimaryKey> inquireKeyByCondition(List<Condition> conditionList);
    
    //public List<Map<String,String>> inquireBySql(String[] tableNames,String sql, String[] values,Map<String,String> dataMap);
    
    public boolean executeUpdateBySql(String[] tableNames,String sql, String[] values,Map<String,String> dataMap); 
    
    public int executeUpdateBySqlAndTakeRows(String[] tableNames,String sql, String[] values,Map<String,String> dataMap); 
    
    public List<Map<String,String>> executeQueryBySql(String[] tableNames,String sql, String[] values,Map<String,String> dataMap);
    
    public String seqValue(String tableName);
    
    public T inqurieByKey(PrimaryKey primaryKey);
    
    
    public List<T> inquireNoCacheByCondition(List<Condition> conditionList);
    
    public List<T> inquireNoCacheByCondition(List<Condition> conditionList,List<Order> orderList);
    
    public PageModel inquirePageNoCacheByCondition(List<Condition> conditionList,int start, int rows);
    
    public PageModel inquirePageNoCacheByCondition(List<Condition> conditionList,int start, int rows,List<Order> orderList);
    
    public int countNoCacheByCondition(List<Condition> conditionList);
    
    public List<PrimaryKey> inquireNoCacheKeyByCondition(List<Condition> conditionList);
    
   // public List<Map<String,String>> inquireNoCacheBySql(String[] tableNames,String sql, String[] values,Map<String,String> dataMap);
    
    public List<Map<String,String>> executeQueryNoCacheBySql(String[] tableNames,String sql, String[] values,Map<String,String> dataMap);
    
    public T inqurieNoCacheByKey(PrimaryKey primaryKey);
    
    
    
}
