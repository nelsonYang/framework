package com.framework.entity.datasource;

import javax.sql.DataSource;

/**
 *
 * @author nelson
 */
public class DataSourceBuilder {
   private String jndi;
   private String url;
   private String userName;
   private String password;

   public DataSource build(DataSourceTypeEnum dataSourceTypeEnum){
       DataSource dataSource = null;
        switch(dataSourceTypeEnum){
            case JDBC: 
                 dataSource = new JDBCDataSource(url,userName,password);
                break;
            case JNDI: 
                 dataSource = new JNDIDataSource(jndi);
                break;
        }
        return dataSource;
   }
   
    /**
     * @return the jndi
     */
    public String getJndi() {
        return jndi;
    }

    /**
     * @param jndi the jndi to set
     */
    public DataSourceBuilder setJndi(String jndi) {
        this.jndi = jndi;
        return this;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public DataSourceBuilder setUrl(String url) {
        this.url = url;
        return this;
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
    public DataSourceBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
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
    public DataSourceBuilder setPassword(String password) {
        this.password = password;
        return this;
    }
   
   
}
