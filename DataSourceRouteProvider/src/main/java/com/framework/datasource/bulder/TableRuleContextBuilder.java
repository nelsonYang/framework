package com.framework.datasource.bulder;

import com.framework.datasource.configuration.ConfigurationManager;
import com.framework.datasource.configuration.XmlConfigurationParser;
import com.framework.datasource.context.TableRuleContext;
import com.framework.datasource.entity.TableRule;
import com.framework.datasource.enumeration.XmlEnum;
import com.frameworkLog.factory.LogFactory;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class TableRuleContextBuilder {
    
    private static final Logger logger = LogFactory.getInstance().getLogger(TableRuleContextBuilder.class);
    
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    public TableRuleContext build() {
        TableRuleContext tableRuleContext = new TableRuleContext();
        XmlConfigurationParser tableRuleConfigurationParser = configurationManager.getXmlConfigurationParser(XmlEnum.TABLE_RULE);
        logger.info("开始解析tableRule.xml");
        tableRuleConfigurationParser.parse("tableRule.xml");
        Map<String, TableRule> tableRuleMap = tableRuleConfigurationParser.getResultMap();
        tableRuleContext.setTableRuleMap(tableRuleMap);
        return tableRuleContext;
    }
}
