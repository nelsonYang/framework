package com.framework.entity.dao;


import com.framework.entity.pojo.Entity;
import com.framework.route.provider.RouteProvider;
import javax.sql.DataSource;

/**
 *
 * @author nelson
 */
public class NoCachceEntityDao<T extends Entity> extends AbstractEntityDao {

    public NoCachceEntityDao(RouteProvider<DataSource> routeProvider,DataSource dataSource,Class<T> clazz,String tableName,String[] fields,String[] keyFields,String countSqlMode,String deleteSqlMode,String inquireSqlMode,String inquireKeyMode,String inquireSqlByKeyMode){
        this.routeProvider = routeProvider;
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
    
}
