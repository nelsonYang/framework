package com.framework.datasource.entity;

import com.framework.datasource.enumeration.RuleEnum;
import com.framework.route.entity.RouteInfo;
import com.frameworkLog.factory.LogFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class TableRule {

    private static final Logger logger = LogFactory.getInstance().getLogger(TableRule.class);
    private String tableName;
    private String defaultPoolNames;
    private String dataBaseName;
    private List<Rule> ruleList;

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the defaultPoolNames
     */
    public String getDefaultPoolNames() {
        return defaultPoolNames;
    }

    /**
     * @param defaultPoolNames the defaultPoolNames to set
     */
    public void setDefaultPoolNames(String defaultPoolNames) {
        this.defaultPoolNames = defaultPoolNames;
    }

    /**
     * @return the dataBaseName
     */
    public String getDataBaseName() {
        return dataBaseName;
    }

    /**
     * @param dataBaseName the dataBaseName to set
     */
    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    /**
     * @return the ruleList
     */
    public List<Rule> getRuleList() {
        return ruleList;
    }

    /**
     * @param ruleList the ruleList to set
     */
    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    public Map<String, RouteInfo<DataSource>> getPoolNames(Map<String, String> parameters) {
        Map<String, RouteInfo<DataSource>> routeMap = new HashMap<String, RouteInfo<DataSource>>(2, 1);
        String poolName;
        String value;
        String[] values;
        Map<String, String> parameterValueMap = new HashMap<String, String>(2, 1);
        for (Rule rule : ruleList) {
            poolName = null;
            value = parameters.get(rule.getParameters());
            logger.debug("value:{}", value);
            if (value != null && !value.isEmpty()) {
                if (value.contains(",")) {
                    values = value.split(",");
                    for (String parameterValue : values) {
                        parameterValueMap.clear();
                        parameterValueMap.put(rule.getParameters(), parameterValue);
                        poolName = this.getPoolName(parameterValueMap);
                        if (poolName == null || poolName.isEmpty()) {
                            poolName = defaultPoolNames;
                        }
                        RouteInfo<DataSource> routeInfo = routeMap.get(poolName);
                        if (routeInfo == null) {
                            routeInfo = new RouteInfo<DataSource>();
                            routeInfo.setDataSourceName(poolName);
                            List<String> valuesList = routeInfo.getValues();
                            valuesList.add(parameterValue);
                            routeMap.put(poolName, routeInfo);
                        } else {
                            List<String> valuesList = routeInfo.getValues();
                            valuesList.add(parameterValue);
                        }
                    }
                } else {
                    poolName = this.getPoolName(parameters);
                    if (poolName == null || poolName.isEmpty()) {
                        poolName = defaultPoolNames;
                    }
                    RouteInfo<DataSource> routeInfo = new RouteInfo();
                    routeInfo.setDataSourceName(poolName);
                    List<String> valuesList = routeInfo.getValues();
                    valuesList.add(value);
                    routeMap.put(poolName, routeInfo);
                }
            } else {
                RouteInfo<DataSource> routeInfo = new RouteInfo();
                routeInfo.setDataSourceName(defaultPoolNames);
                routeMap.put(defaultPoolNames, routeInfo);
            }
        }
        logger.debug("routeMap:{}", routeMap);
        return routeMap;
    }

    public String getPoolName(Map<String, String> parameters) {
        String poolName = null;
        for (Rule rule : ruleList) {
            poolName = this.getPoolNameByRule(rule, parameters);
            if (poolName != null && !poolName.isEmpty()) {
                break;
            }
        }
        return poolName;
    }

    public String getPoolNameByRule(Rule rule, Map<String, String> parameters) {
        String poolName = null;
        logger.debug("type:{}", rule.getType());
        if (RuleEnum.RANGE.toString().equals(rule.getType())) {
            boolean isSuccess = this.getRangePoolName(rule, parameters);
            if (isSuccess) {
                poolName = rule.getDbPoolName();
            }
        } else if (RuleEnum.SEQ.toString().equals(rule.getType())) {
            poolName = rule.getDbPoolName();
        } else if (RuleEnum.HASH.toString().equals(rule.getType())) {
            poolName = this.getHashPool(rule, parameters);
        } else if (RuleEnum.FIXED.toString().equals(rule.getType())) {
            poolName = rule.getDbPoolName();
        } else {
            boolean isAndSuccess = true;
            List<Rule> andRuleList = rule.getAndRuleList();
            boolean isSuccess;
            for (Rule andRule : andRuleList) {
                if (RuleEnum.RANGE.toString().equals(andRule.getType())) {
                    isSuccess = this.getRangePoolName(rule, parameters);
                    if (!isSuccess) {
                        isAndSuccess = false;
                        break;
                    }
                } else if (RuleEnum.HASH.toString().equals(andRule.getType())) {
                    isSuccess = this.getHashPoolName(rule, parameters);
                    if (!isSuccess) {
                        isAndSuccess = false;
                        break;
                    }
                }
            }
            List<Rule> orRuleList = rule.getOrRuleList();
            boolean isOrSuccess;
            if (!orRuleList.isEmpty()) {
                isOrSuccess = false;
                for (Rule orRule : orRuleList) {
                    if (RuleEnum.RANGE.toString().equals(orRule.getType())) {
                        isSuccess = this.getRangePoolName(rule, parameters);
                        if (isSuccess) {
                            isOrSuccess = true;
                            break;
                        }
                    } else if (RuleEnum.HASH.toString().equals(orRule.getType())) {
                        isSuccess = this.getHashPoolName(rule, parameters);
                        if (isSuccess) {
                            isOrSuccess = true;
                            break;
                        }
                    }
                }
            } else {
                isOrSuccess = true;
            }
            if (isOrSuccess && isAndSuccess) {
                poolName = rule.getDbPoolName();
            }
        }
        logger.debug("poolName:{}", poolName);
        return poolName;
    }

    private boolean getRangePoolName(Rule rule, Map<String, String> parameters) {
        String value;
        String maxValue;
        String minValue;
        String poolName = null;
        boolean isSuccess = false;
        value = parameters.get(rule.getParameters());
        logger.debug("value:{}", value);
        if (value != null && !value.isEmpty()) {
            boolean minSuccess = true;
            boolean maxSuccess = true;
            minValue = rule.getMinValue();
            maxValue = rule.getMaxValue();
            if (minValue != null && !minValue.isEmpty()) {
                if (Integer.parseInt(minValue) >= Integer.parseInt(value)) {
                    minSuccess = false;
                }
            }
            if (maxValue != null && !maxValue.isEmpty()) {
                if (Integer.parseInt(value) >= Integer.parseInt(maxValue)) {
                    maxSuccess = false;
                }
            }
            if (maxSuccess && minSuccess) {
                isSuccess = true;
            }
        }
        logger.debug("isSuccess:{}", isSuccess);
        return isSuccess;
    }

    private boolean getHashPoolName(Rule rule, Map<String, String> parameters) {
        String poolName = null;
        String value;
        boolean isSuccess = false;
        value = parameters.get(rule.getParameters());
        logger.debug("value:{}", value);
        int hashValue = -1;
        if (value != null && !value.isEmpty()) {
            hashValue = Math.abs(Integer.parseInt(value) % Integer.parseInt(rule.getHashValue()));
        }
        if (hashValue != -1) {
            poolName = rule.getHashResult().get(String.valueOf(hashValue));
            if (poolName != null && !poolName.isEmpty()) {
                isSuccess = true;
            }
        }
        logger.debug("isSuccess:{}", isSuccess);
        return isSuccess;
    }

    private String getHashPool(Rule rule, Map<String, String> parameters) {
        String poolName = null;
        String value;
        value = parameters.get(rule.getParameters());
        logger.debug("value:{}", value);
        int hashValue = -1;
        if (value != null && !value.isEmpty()) {
            hashValue = Math.abs(Integer.parseInt(value) % Integer.parseInt(rule.getHashValue()));
        }
        if (hashValue != -1) {
            poolName = rule.getHashResult().get(String.valueOf(hashValue));

        }
        logger.debug("poolName:{}", poolName);
        return poolName;
    }
}
