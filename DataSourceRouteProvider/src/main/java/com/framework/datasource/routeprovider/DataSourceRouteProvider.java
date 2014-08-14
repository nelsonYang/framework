package com.framework.datasource.routeprovider;

import com.framework.datasource.bulder.DataSourceContextBuilder;
import com.framework.datasource.bulder.TableRuleContextBuilder;
import com.framework.datasource.context.DataSourceContext;
import com.framework.datasource.context.TableRuleContext;
import com.framework.datasource.entity.TableRule;
import com.framework.route.entity.RouteInfo;
import com.framework.route.provider.RouteProvider;
import com.frameworkLog.factory.LogFactory;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class DataSourceRouteProvider implements RouteProvider<DataSource> {

    private static final Logger logger = LogFactory.getInstance().getLogger(DataSourceRouteProvider.class);
    private DataSourceContext dataSourceContext;
    private TableRuleContext tableRuleContext;

    public RouteInfo<DataSource> getRoute(String keyword, Map<String, String> parameters) {
        logger.debug("keyword:{}", keyword);
        TableRule tableRule = tableRuleContext.getTableRule(keyword);
        String dataSourceName = tableRule.getPoolName(parameters);
        if (dataSourceName == null || "".equals(dataSourceName)) {
            String defaultPoolNames = tableRule.getDefaultPoolNames();
            if (!defaultPoolNames.contains(",")) {
                dataSourceName = defaultPoolNames.split(",")[0];
            } else {
                dataSourceName = defaultPoolNames;
            }
        }
        logger.debug("datasourcename:{}", dataSourceName);
        DataSource dataSource = dataSourceContext.getDataSource(dataSourceName);
        RouteInfo<DataSource> routeInfo = new RouteInfo<DataSource>();
        routeInfo.setDataSource(dataSource);
        routeInfo.setDataSourceName(dataSourceName);
        return routeInfo;
    }

    /**
     *
     * @param keyword
     * @param parameters
     * @return
     */
    public Map<String, RouteInfo<DataSource>> getRoutes(String keyword, Map<String, String> parameters) {
        logger.debug("keyword:{}", keyword);
        TableRule tableRule = tableRuleContext.getTableRule(keyword);
        Map<String, RouteInfo<DataSource>> routeInfoMap = tableRule.getPoolNames(parameters);
        Set<Map.Entry<String, RouteInfo<DataSource>>> routeInfoSet = routeInfoMap.entrySet();
        DataSource dataSource;
        RouteInfo routeInfo;
        for (Map.Entry<String, RouteInfo<DataSource>> entry : routeInfoSet) {
            dataSource = dataSourceContext.getDataSource(entry.getKey());
            routeInfo = entry.getValue();
            routeInfo.setDataSource(dataSource);
        }
        return routeInfoMap;
    }

    public void registerRoutes() {
        DataSourceContextBuilder dataSourceContextBuilder = new DataSourceContextBuilder();
        dataSourceContext = dataSourceContextBuilder.build();
        TableRuleContextBuilder tableRuleContextBuilder = new TableRuleContextBuilder();
        tableRuleContext = tableRuleContextBuilder.build();
    }
}
