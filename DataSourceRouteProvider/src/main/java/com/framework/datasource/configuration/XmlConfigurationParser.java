package com.framework.datasource.configuration;

import java.util.Map;

/**
 * @author nelson
 */
public interface XmlConfigurationParser<T>{
    public void parse(String fileName);
    public Map<String,T> getResultMap();
}
