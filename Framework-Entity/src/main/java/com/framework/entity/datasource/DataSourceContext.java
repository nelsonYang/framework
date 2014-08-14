package com.framework.entity.datasource;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author nelson
 */
public class DataSourceContext {

    private final Map<DataSourceTypeEnum, DataSource> dataSourceMap = new HashMap<DataSourceTypeEnum, DataSource>(2, 1);
 //   private DataSourceContext dataSourceContext = new DataSourceContext();
    public void putDataSource(DataSourceTypeEnum dataSourceTypeEnum, DataSource dataSource) {
        this.dataSourceMap.put(dataSourceTypeEnum, dataSource);
    }

    public DataSource getDataSource(DataSourceTypeEnum dataSourceTypeEnum) {
        return this.dataSourceMap.get(dataSourceTypeEnum);
    }
}
