package com.framework.datasource.entity;

import java.util.Map;

/**
 * @author nelson
 */
public class DataBasePool {

    private String dbServerName;
    private String dbServerParentName;
    private String userName;
    private String password;
    private String port;
    private String host;
    private String databaseName;
    private String driverClass;
    private int maxActive;
    private int minActive;
    private int maxIdleTime;
    private int minIdle;
    private boolean isAbstract = false;
    private String type; //jdbc jndi
    private String jndiName;

    /**
     * @return the dbServerName
     */
    public String getDbServerName() {
        return dbServerName;
    }

    /**
     * @param dbServerName the dbServerName to set
     */
    public void setDbServerName(String dbServerName) {
        this.dbServerName = dbServerName;
    }

    /**
     * @return the dbServerParentName
     */
    public String getDbServerParentName() {
        return dbServerParentName;
    }

    /**
     * @param dbServerParentName the dbServerParentName to set
     */
    public void setDbServerParentName(String dbServerParentName) {
        this.dbServerParentName = dbServerParentName;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the databaseName
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * @param databaseName the databaseName to set
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * @return the maxActive
     */
    public int getMaxActive() {
        return maxActive;
    }

    /**
     * @param maxActive the maxActive to set
     */
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    /**
     * @return the minActive
     */
    public int getMinActive() {
        return minActive;
    }

    /**
     * @param minActive the minActive to set
     */
    public void setMinActive(int minActive) {
        this.minActive = minActive;
    }

    /**
     * @return the maxIdle
     */
    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    /**
     * @param maxIdle the maxIdle to set
     */
    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    /**
     * @return the minIdle
     */
    public int getMinIdle() {
        return minIdle;
    }

    /**
     * @param minIdle the minIdle to set
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public void parseMap(Map<String, String> entityMap) {
        this.databaseName = entityMap.get("database");
        this.dbServerName = entityMap.get("name");
        this.dbServerParentName = entityMap.get("dbServerParentName");
        this.driverClass = entityMap.get("driverClass");
        this.host = entityMap.get("host");
        this.userName = entityMap.get("user");
        this.password = entityMap.get("password");
        this.port = entityMap.get("port");
        this.type = entityMap.get("type");
        this.jndiName = entityMap.get("jndiName");
        if (entityMap.get("maxActive") != null) {
            this.maxActive = Integer.parseInt(entityMap.get("maxActive"));
        }
        if (entityMap.get("maxIdleTime") != null) {
            this.maxIdleTime = Integer.parseInt(entityMap.get("maxIdleTime"));
        }
        if (entityMap.get("minActive") != null) {
            this.minActive = Integer.parseInt(entityMap.get("minActive"));
        }
        if (entityMap.get("minIdle") != null) {
            this.minIdle = Integer.parseInt(entityMap.get("minIdle"));
        }
        if(entityMap.get("abstractive") != null){
            this.isAbstract = Boolean.parseBoolean(entityMap.get("abstractive"));
        }

    }

    public String jdbcUrl() {
        return "jdbc:mysql://".concat(host).concat(":").concat(port).concat("/").concat(databaseName).concat("?useUnicode=true&characterEncoding=utf8&autoReconnect=true");
    }

    /**
     * @return the driverClass
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * @param driverClass the driverClass to set
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * @return the isAbstract
     */
    public boolean isIsAbstract() {
        return isAbstract;
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
     * @return the jndiName
     */
    public String getJndiName() {
        return jndiName;
    }

    /**
     * @param jndiName the jndiName to set
     */
    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }
}
