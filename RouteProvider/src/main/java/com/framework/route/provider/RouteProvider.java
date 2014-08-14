package com.framework.route.provider;

import com.framework.route.entity.RouteInfo;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface RouteProvider<T>{
    public RouteInfo<T> getRoute(String keyword,Map<String,String> parameters);
    public Map<String,RouteInfo<T>>  getRoutes(String keyword,Map<String,String> parameters);
    public void registerRoutes();
}
