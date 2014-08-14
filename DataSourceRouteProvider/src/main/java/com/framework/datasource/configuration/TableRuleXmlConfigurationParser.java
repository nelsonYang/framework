package com.framework.datasource.configuration;

import com.framework.datasource.entity.Rule;
import com.framework.datasource.entity.TableRule;
import com.framework.datasource.enumeration.RuleEnum;
import com.frameworkLog.factory.LogFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;

/**
 * @author nelson
 */
public class TableRuleXmlConfigurationParser implements XmlConfigurationParser {

    private static final Logger logger = LogFactory.getInstance().getLogger(TableRuleXmlConfigurationParser.class);
    private Map<String, TableRule> tableRuleMap = new HashMap<String, TableRule>(128, 1);

    public void parse(String fileName) {
        logger.debug("fileName:{}", fileName);
        try {
            SAXReader reader = new SAXReader();
            InputStream is = this.getClass().getResourceAsStream("/" + fileName);
            Document document = reader.read(is);
            Element rootElement = document.getRootElement();
            List<Element> tableRuleElementLists = rootElement.elements("tableRule");
            List<Element> ruleElementList;
            List<Element> childElementList;
            List<Element> orElementList;
            List<Rule> orRuleList;
            List<Element> andElementList;
            List<Rule> andRuleList;
            Attribute ruleNameAttribute;
            Attribute tableRuleNameAttribute;
            Attribute tableRuleDatabaseAttribute;
            Attribute tabeRuleDefaultPoolAttribute;
            Map<String, String> ruleMap;
            String[] tableRuleNames;
            String tableRuleName;
            TableRule tableRuleEntity;
            String ruleName;
            String type;
            String parameters = null;
            List<Rule> ruleList;
            Rule rule;
            String hashValue = null;
            Element parameterElement;
            Map<String, String> hashResultMap = null;
            for (Element tableRuleElement : tableRuleElementLists) {
                tableRuleNameAttribute = tableRuleElement.attribute("name");
                tableRuleName = tableRuleNameAttribute.getValue();
                tableRuleDatabaseAttribute = tableRuleElement.attribute("database");
                tabeRuleDefaultPoolAttribute = tableRuleElement.attribute("defaultPools");
                if (!tableRuleName.contains(",")) {
                    tableRuleEntity = new TableRule();
                    tableRuleEntity.setTableName(tableRuleName);
                    tableRuleEntity.setDataBaseName(tableRuleDatabaseAttribute.getValue());
                    tableRuleEntity.setDefaultPoolNames(tabeRuleDefaultPoolAttribute.getValue());
                    ruleElementList = tableRuleElement.elements("rule");
                    ruleList = new ArrayList<Rule>(ruleElementList.size());
                    for (Element ruleElement : ruleElementList) {
                        ruleMap = new HashMap<String, String>(4, 1);
                        ruleNameAttribute = ruleElement.attribute("name");
                        ruleName = ruleNameAttribute.getValue().trim();
                        type = ruleElement.element("type").getTextTrim();
                        logger.debug("ruleName:{}", ruleName);
                        logger.debug("type:{}", type);
                        parameterElement = ruleElement.element("parameters");
                        if (parameterElement != null) {
                            parameters = parameterElement.getTextTrim();
                        }
                        rule = new Rule(ruleName, type, parameters);
                        if (RuleEnum.RANGE.toString().equals(type)) {
                            childElementList = ruleElement.elements();
                            for (Element element : childElementList) {
                                ruleMap.put(element.getName(), element.getTextTrim());
                            }
                            rule.parseMap(ruleMap);
                            ruleList.add(rule);
                        } else if (RuleEnum.SEQ.toString().equals(type)) {
                            String poolName = ruleElement.element("defaultPools").getTextTrim();
                            rule.setDbPoolName(poolName);
                            ruleList.add(rule);
                        } else if (RuleEnum.FIXED.toString().equals(type)) {
                            String poolName = ruleElement.element("defaultPools").getTextTrim();
                            rule.setDbPoolName(poolName);
                            ruleList.add(rule);
                        } else if (RuleEnum.MIXED.toString().equals(type)) {
                            orElementList = ruleElement.elements("or");
                            if (orElementList != null && !orElementList.isEmpty()) {
                                orRuleList = new ArrayList<Rule>(orElementList.size());
                                for (Element orElement : orElementList) {
                                    type = orElement.element("type").getTextTrim();
                                    parameters = orElement.element("parameters").getTextTrim();
                                    rule = new Rule(ruleName, type, parameters);
                                    childElementList = orElement.elements();
                                    for (Element element : childElementList) {
                                        ruleMap.put(element.getName(), element.getTextTrim());
                                    }
                                    rule.parseMap(ruleMap);
                                    orRuleList.add(rule);
                                }
                                rule.setOrRuleList(orRuleList);
                            }
                            andElementList = ruleElement.elements("and");
                            if (andElementList != null && !andElementList.isEmpty()) {
                                andRuleList = new ArrayList<Rule>(andElementList.size());
                                for (Element andElement : andElementList) {
                                    type = andElement.element("type").getTextTrim();
                                    parameters = andElement.element("parameters").getTextTrim();
                                    rule = new Rule(ruleName, type, parameters);
                                    childElementList = andElement.elements();
                                    for (Element element : childElementList) {
                                        ruleMap.put(element.getName(), element.getTextTrim());
                                    }
                                    rule.parseMap(ruleMap);
                                    andRuleList.add(rule);
                                }
                                rule.setAndRuleList(andRuleList);
                            }
                            ruleList.add(rule);
                        } else {
                            hashValue = ruleElement.element("hashValue").getTextTrim();
                            List<Element> hashResultList = ruleElement.elements("hashResult");
                            hashResultMap = new HashMap<String, String>(hashResultList.size(), 1);
                            String value;
                            for (Element resultElement : hashResultList) {
                                value = resultElement.element("value").getTextTrim();
                                if (value.contains(",")) {
                                    String[] values = value.split(",");
                                    for (String key : values) {
                                        hashResultMap.put(key, resultElement.element("defaultPools").getTextTrim());
                                    }
                                } else {
                                    hashResultMap.put(value, resultElement.element("defaultPools").getTextTrim());
                                }
                            }
                            rule.setHashValue(hashValue);
                            rule.setHashResult(hashResultMap);
                            ruleList.add(rule);
                        }
                    }
                    tableRuleEntity.setRuleList(ruleList);
                    tableRuleMap.put(tableRuleName, tableRuleEntity);
                } else {
                    tableRuleNames = tableRuleName.split(",");
                    for (String tableRule : tableRuleNames) {
                        tableRuleEntity = new TableRule();
                        tableRuleEntity.setTableName(tableRule);
                        tableRuleEntity.setDataBaseName(tableRuleDatabaseAttribute.getValue());
                        tableRuleEntity.setDefaultPoolNames(tabeRuleDefaultPoolAttribute.getValue());
                        ruleElementList = tableRuleElement.elements("rule");
                        ruleList = new ArrayList<Rule>(ruleElementList.size());
                        for (Element ruleElement : ruleElementList) {
                            ruleMap = new HashMap<String, String>(4, 1);
                            ruleNameAttribute = ruleElement.attribute("name");
                            ruleName = ruleNameAttribute.getValue().trim();
                            type = ruleElement.element("type").getTextTrim();
                            parameterElement = ruleElement.element("parameters");
                            if (parameterElement != null) {
                                parameters = parameterElement.getTextTrim();
                            }
                            rule = new Rule(ruleName, type, parameters);
                            if (RuleEnum.RANGE.toString().equals(type)) {
                                childElementList = ruleElement.elements();
                                for (Element element : childElementList) {
                                    ruleMap.put(element.getName(), element.getTextTrim());
                                }
                                rule.parseMap(ruleMap);
                                ruleList.add(rule);
                            } else if (RuleEnum.FIXED.toString().equals(type)) {
                                String poolName = ruleElement.element("defaultPools").getTextTrim();
                                rule.setDbPoolName(poolName);
                                ruleList.add(rule);
                            } else if (RuleEnum.MIXED.toString().equals(type)) {
                                orElementList = ruleElement.elements("or");
                                if (orElementList != null && !orElementList.isEmpty()) {
                                    orRuleList = new ArrayList<Rule>(orElementList.size());
                                    for (Element orElement : orElementList) {
                                        type = orElement.element("type").getTextTrim();
                                        parameters = orElement.element("parameters").getTextTrim();
                                        rule = new Rule(ruleName, type, parameters);
                                        childElementList = orElement.elements();
                                        for (Element element : childElementList) {
                                            ruleMap.put(element.getName(), element.getTextTrim());
                                        }
                                        rule.parseMap(ruleMap);
                                        orRuleList.add(rule);
                                    }
                                    rule.setOrRuleList(orRuleList);
                                }
                                andElementList = ruleElement.elements("and");
                                if (andElementList != null && !andElementList.isEmpty()) {
                                    andRuleList = new ArrayList<Rule>(andElementList.size());
                                    for (Element andElement : andElementList) {
                                        type = andElement.element("type").getTextTrim();
                                        parameters = andElement.element("parameters").getTextTrim();
                                        rule = new Rule(ruleName, type, parameters);
                                        childElementList = andElement.elements();
                                        for (Element element : childElementList) {
                                            ruleMap.put(element.getName(), element.getTextTrim());
                                        }
                                        rule.parseMap(ruleMap);
                                        andRuleList.add(rule);
                                    }
                                    rule.setAndRuleList(andRuleList);
                                }
                                ruleList.add(rule);
                            } else {
                                hashValue = ruleElement.element("hashValue").getTextTrim();
                                List<Element> hashResultList = ruleElement.elements("hashResult");
                                hashResultMap = new HashMap<String, String>(hashResultList.size(), 1);
                                String value;
                                for (Element resultElement : hashResultList) {
                                    value = resultElement.element("value").getTextTrim();
                                    if (value.contains(",")) {
                                        String[] values = value.split(",");
                                        for (String key : values) {
                                            hashResultMap.put(key, resultElement.element("defaultPools").getTextTrim());
                                        }
                                    } else {
                                        hashResultMap.put(value, resultElement.element("defaultPools").getTextTrim());
                                    }
                                }
                                rule.setHashValue(hashValue);
                                rule.setHashResult(hashResultMap);
                                ruleList.add(rule);
                            }

                        }
                        tableRuleEntity.setRuleList(ruleList);
                        tableRuleMap.put(tableRule, tableRuleEntity);
                    }
                }
            }

        } catch (DocumentException ex) {
            ex.printStackTrace();
            logger.error("解析tableRule.xml出错", ex);
            throw new RuntimeException("解析tableRule出错");
        }
    }

    public Map<String, TableRule> getResultMap() {
        return this.tableRuleMap;
    }
}
