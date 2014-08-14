package com.framework.datasource.context;

import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author nelson
 */
public class DataSourceContext {
    private Map<String,DataSource> dataSourceMap;

    /**
     * @param dataSourceMap the dataSourceMap to set
     */
    public void setDataSourceMap(Map<String,DataSource> dataSourceMap) {
        this.dataSourceMap = dataSourceMap;
    }
    
    public DataSource getDataSource(String dataSourceName){
        return this.dataSourceMap.get(dataSourceName);
    }
}
