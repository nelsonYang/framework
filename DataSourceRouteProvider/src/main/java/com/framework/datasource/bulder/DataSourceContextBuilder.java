package com.framework.datasource.bulder;

import com.framework.datasource.configuration.ConfigurationManager;
import com.framework.datasource.configuration.XmlConfigurationParser;
import com.framework.datasource.context.DataSourceContext;
import com.framework.datasource.entity.DataBasePool;
import com.framework.datasource.enumeration.XmlEnum;
import com.frameworkLog.factory.LogFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class DataSourceContextBuilder {

    private static final Logger logger = LogFactory.getInstance().getLogger(DataSourceContextBuilder.class);
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    public DataSourceContext build() {
        DataSourceContext dataSourceContext = new DataSourceContext();
        XmlConfigurationParser dbServerConfigurationParser = configurationManager.getXmlConfigurationParser(XmlEnum.DB_SERVER);
        logger.info("开始解析dbServer.xml");
        dbServerConfigurationParser.parse("dbServer.xml");
        Map<String, DataBasePool> dataBasePoolMap = dbServerConfigurationParser.getResultMap();
        Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>(8, 1);
        String dataSourceName;
        DataBasePool dataBasePool;
        String type;
        String jndiName;
        ComboPooledDataSource comboPooledDataSource;
        DataSource dataSource;
        try {
            for (Entry<String, DataBasePool> entry : dataBasePoolMap.entrySet()) {
                //构建链接池
                dataSourceName = entry.getKey();
                dataBasePool = entry.getValue();
                logger.debug("dataBasePool:{}", dataBasePool.toString());
                type = dataBasePool.getType();
                logger.debug("type:{}", type);
                if ("jdbc".equals(type)) {
                    if (!dataBasePool.isIsAbstract()) {
                        comboPooledDataSource = new ComboPooledDataSource();
                        comboPooledDataSource.setJdbcUrl(dataBasePool.jdbcUrl());
                        comboPooledDataSource.setDataSourceName(dataSourceName);
                        comboPooledDataSource.setDriverClass(dataBasePool.getDriverClass());
                        comboPooledDataSource.setMaxPoolSize(dataBasePool.getMaxActive());
                        comboPooledDataSource.setMinPoolSize(dataBasePool.getMinActive());
                        comboPooledDataSource.setUser(dataBasePool.getUserName());
                        comboPooledDataSource.setPassword(dataBasePool.getPassword());
                        comboPooledDataSource.setInitialPoolSize(dataBasePool.getMinActive());
                        comboPooledDataSource.setMaxIdleTime(dataBasePool.getMaxIdleTime());
                        dataSourceMap.put(dataSourceName, comboPooledDataSource);
                    }
                } else {
                    jndiName = dataBasePool.getJndiName();
                    logger.debug("jndiName:{}", jndiName);
                    if (jndiName != null && !jndiName.isEmpty()) {
                        InitialContext initCtx = new InitialContext();
                        dataSource = (DataSource) initCtx.lookup(jndiName);
                        dataSourceMap.put(dataSourceName, dataSource);
                    }
                }

            }
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
            logger.error("dataSourceContextBuilder:{}", ex);
            throw new RuntimeException("driver Class not found");
        } catch (NamingException ex) {
            ex.printStackTrace();
            logger.error("jndi dataSource.:{}", ex);
            throw new RuntimeException("jndi dataSource. cause naming exception ");
        }
        dataSourceContext.setDataSourceMap(dataSourceMap);
        return dataSourceContext;
    }
}
