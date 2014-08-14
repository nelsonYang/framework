package com.framework.datasource.configuration;

import com.framework.datasource.entity.DataBasePool;
import com.frameworkLog.factory.LogFactory;
import java.io.InputStream;
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
 *
 * @author nelson
 */
public class DBServerXmlConfigurationParser implements XmlConfigurationParser {

    private static final Logger logger = LogFactory.getInstance().getLogger(DBServerXmlConfigurationParser.class);
    private final Map<String, DataBasePool> databasePoolMap = new HashMap<String, DataBasePool>(8, 1);

    public void parse(String fileName) {
        logger.debug("fileName:{}", fileName);
        try {
            SAXReader reader = new SAXReader();
            InputStream is = this.getClass().getResourceAsStream("/" + fileName);
            Document document = reader.read(is);
            Element rootElement = document.getRootElement();
            List<Element> dbServerElementLists = rootElement.elements("dbServer");
            List<Element> propertyElementList = null;
            String dbServerName;
            String dbServerParentName = null;
            String abstractive;
            Attribute parentAttribute;
            Attribute abstractiveAttribute;
            Attribute propertyAttribute;
            DataBasePool dataBasePool;
            String propertyName;
            String propertyValue;
            String type;
            Map<String, String> dataBasePoolMap;
            for (Element element : dbServerElementLists) {
                type = element.attribute("type").getValue();
                logger.debug("type:{}", type);
                if ("jdbc".equals(type)) {
                    abstractive = null;
                    dataBasePoolMap = new HashMap<String, String>(16, 1);
                    dataBasePool = new DataBasePool();
                    dbServerName = element.attribute("name").getValue();
                    parentAttribute = element.attribute("parent");
                    abstractiveAttribute = element.attribute("abstractive");
                    if (parentAttribute != null) {
                        dbServerParentName = parentAttribute.getValue();
                    }
                    if (abstractiveAttribute != null) {
                        abstractive = abstractiveAttribute.getValue();
                    }
                    dataBasePoolMap.put("dbServerName", dbServerName);
                    dataBasePoolMap.put("dbServerParentName", dbServerParentName);
                    dataBasePoolMap.put("abstractive", abstractive);
                    propertyElementList = element.elements("property");
                    for (Element propertyElement : propertyElementList) {
                        propertyAttribute = propertyElement.attribute("name");
                        propertyName = propertyAttribute.getValue();
                        propertyValue = propertyElement.getTextTrim();
                        dataBasePoolMap.put(propertyName, propertyValue);
                    }
                    dataBasePool.parseMap(dataBasePoolMap);
                    logger.debug("dataBasePool:{}", dataBasePool);
                    databasePoolMap.put(dbServerName, dataBasePool);
                    DataBasePool dataBasePoolEntity;
                    DataBasePool parentDataBasePool;
                    String driverClass;
                    String port;
                    String userName;
                    String password;
                    String host;
                    String databaseName;
                    int maxActive;
                    int minActive;
                    int maxIdle;
                    int minIdle;
                    for (Map.Entry<String, DataBasePool> entry : databasePoolMap.entrySet()) {
                        dataBasePoolEntity = entry.getValue();
                        logger.debug("dataBasePoolEntity:{}", dataBasePoolEntity);
                        if (dataBasePoolEntity.getDbServerParentName() != null) {
                            parentDataBasePool = databasePoolMap.get(dataBasePoolEntity.getDbServerParentName());
                            port = dataBasePoolEntity.getPort();
                            userName = dataBasePoolEntity.getUserName();
                            password = dataBasePoolEntity.getPassword();
                            host = dataBasePoolEntity.getHost();
                            databaseName = dataBasePoolEntity.getDatabaseName();
                            maxActive = dataBasePoolEntity.getMaxActive();
                            minActive = dataBasePoolEntity.getMinActive();
                            maxIdle = dataBasePoolEntity.getMaxIdleTime();
                            minIdle = dataBasePoolEntity.getMinIdle();
                            driverClass = dataBasePoolEntity.getDriverClass();
                            if (port == null || "".equals(port)) {
                                dataBasePoolEntity.setPort(parentDataBasePool.getPort());
                            }
                            if (userName == null || "".equals(userName)) {
                                dataBasePoolEntity.setUserName(parentDataBasePool.getUserName());
                            }
                            if (password == null || "".equals(password)) {
                                dataBasePoolEntity.setPassword(parentDataBasePool.getPassword());
                            }
                            if (host == null || "".equals(host)) {
                                dataBasePoolEntity.setHost(parentDataBasePool.getHost());
                            }
                            if (driverClass == null || "".equals(driverClass)) {
                                dataBasePoolEntity.setDriverClass(parentDataBasePool.getDriverClass());
                            }
                            if (databaseName == null || "".equals(databaseName)) {
                                dataBasePoolEntity.setDatabaseName(parentDataBasePool.getDatabaseName());
                            }
                            if (maxActive <= 0) {
                                dataBasePoolEntity.setMaxActive(parentDataBasePool.getMaxActive());
                            }
                            if (minActive <= 0) {
                                dataBasePoolEntity.setMinActive(parentDataBasePool.getMinActive());
                            }
                            if (maxIdle <= 0) {
                                dataBasePoolEntity.setMaxIdleTime(parentDataBasePool.getMaxIdleTime());
                            }
                            if (minIdle <= 0) {
                                dataBasePoolEntity.setMinIdle(parentDataBasePool.getMinIdle());
                            }
                        }
                    }
                } else {
                    dataBasePoolMap = new HashMap<String, String>(16, 1);
                    dataBasePool = new DataBasePool();
                    dbServerName = element.attribute("name").getValue();
                    dataBasePoolMap.put("dbServerName", dbServerName);
                    propertyElementList = element.elements("property");
                    for (Element propertyElement : propertyElementList) {
                        propertyAttribute = propertyElement.attribute("name");
                        propertyName = propertyAttribute.getValue();
                        propertyValue = propertyElement.getTextTrim();
                        dataBasePoolMap.put(propertyName, propertyValue);
                    }
                    dataBasePool.parseMap(dataBasePoolMap);
                    logger.debug("dataBasePoolMap:{}", dataBasePoolMap);
                    databasePoolMap.put(dbServerName, dataBasePool);
                }
            }
        } catch (DocumentException ex) {
            ex.printStackTrace();
            logger.error("解析dbServer.xml出错", ex);
            throw new RuntimeException("解析dbServer出错");
        }
    }

    public Map<String, DataBasePool> getResultMap() {
        return this.databasePoolMap;
    }
}
