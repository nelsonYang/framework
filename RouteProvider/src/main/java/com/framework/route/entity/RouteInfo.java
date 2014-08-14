package com.framework.route.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class RouteInfo<T> {

    private T dataSource;
    private String dataSourceName;
    private List<String> values = new ArrayList<String>();

    /**
     * @return the dataSource
     */
    public T getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(T dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return the dataSourceName
     */
    public String getDataSourceName() {
        return dataSourceName;
    }

    /**
     * @param dataSourceName the dataSourceName to set
     */
    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * @return the values
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<String> values) {
        this.values = values;
    }
}
