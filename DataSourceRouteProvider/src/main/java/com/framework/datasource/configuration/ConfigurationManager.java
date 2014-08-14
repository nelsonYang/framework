package com.framework.datasource.configuration;

import com.framework.datasource.enumeration.XmlEnum;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class ConfigurationManager {
    private static ConfigurationManager instance = new ConfigurationManager();
    private static final Map<XmlEnum, XmlConfigurationParser> xmlConfigurationParserMap = new HashMap<XmlEnum, XmlConfigurationParser>(2, 1);

    static {
        xmlConfigurationParserMap.put(XmlEnum.DB_SERVER, new DBServerXmlConfigurationParser());
        xmlConfigurationParserMap.put(XmlEnum.TABLE_RULE, new TableRuleXmlConfigurationParser());
    }

    public XmlConfigurationParser getXmlConfigurationParser(XmlEnum xmlEnum) {
        return xmlConfigurationParserMap.get(xmlEnum);
    }
    
    public static ConfigurationManager getInstance(){
        return instance;
    }
}
