package com.framework.entity.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author nelson
 */
public class JDBCDataSource implements DataSource {

    private String driverClass = "com.mysql.jdbc.Driver";
    private String jdbcURL;
    private String userName;
    private String password;

    public JDBCDataSource(String jdbcURL, String userName, String password) {
        this.jdbcURL = jdbcURL;
        this.userName = userName;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return this.getJDBCConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return this.getJDBCConnection();
    }
    //

    private Connection getJDBCConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(jdbcURL, userName, password);
        } catch (ClassNotFoundException ex) {
        }

        return connection;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return DriverManager.getLogWriter();
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        DriverManager.setLogWriter(out);
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        DriverManager.setLoginTimeout(seconds);
    }

    public int getLoginTimeout() throws SQLException {
        return DriverManager.getLoginTimeout();
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
