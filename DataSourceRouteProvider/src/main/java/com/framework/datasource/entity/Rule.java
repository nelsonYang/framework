package com.framework.datasource.entity;

import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class Rule {

    public Rule(String name, String type, String parameters) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;
    }
    private String name;
    private String parameters;
    private String expression;
    private String dbPoolName;
    private String type;
    private String minValue;
    private String maxValue;
    private List<Rule> orRuleList;
    private List<Rule> andRuleList;
    private String hashValue;
    private Map<String, String> hashResult;

    /**
     * @return the parameters
     */
    public String getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * @return the dbPoolName
     */
    public String getDbPoolName() {
        return dbPoolName;
    }

    /**
     * @param dbPoolName the dbPoolName to set
     */
    public void setDbPoolName(String dbPoolName) {
        this.dbPoolName = dbPoolName;
    }

    public void parseMap(Map<String, String> entityMap) {
        this.dbPoolName = entityMap.get("defaultPools");
        this.minValue = entityMap.get("minValue");
        this.maxValue = entityMap.get("maxValue");
        this.hashValue = entityMap.get("hashValue");

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the minValue
     */
    public String getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the maxVlaue
     */
    public String getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxVlaue the maxVlaue to set
     */
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the orRuleList
     */
    public List<Rule> getOrRuleList() {
        return orRuleList;
    }

    /**
     * @param orRuleList the orRuleList to set
     */
    public void setOrRuleList(List<Rule> orRuleList) {
        this.orRuleList = orRuleList;
    }

    /**
     * @return the andRuleList
     */
    public List<Rule> getAndRuleList() {
        return andRuleList;
    }

    /**
     * @param andRuleList the andRuleList to set
     */
    public void setAndRuleList(List<Rule> andRuleList) {
        this.andRuleList = andRuleList;
    }

    /**
     * @return the hashValue
     */
    public String getHashValue() {
        return hashValue;
    }

    /**
     * @param hashValue the hashValue to set
     */
    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    /**
     * @return the hashResult
     */
    public Map<String, String> getHashResult() {
        return hashResult;
    }

    /**
     * @param hashResult the hashResult to set
     */
    public void setHashResult(Map<String, String> hashResult) {
        this.hashResult = hashResult;
    }
}
