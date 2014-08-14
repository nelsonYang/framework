package com.framework.entity.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author nelson
 */
public class JNDIDataSource implements DataSource {

    private String jndi;
    private String jdbcURL;
    private String userName;
    private String password;

    public JNDIDataSource(String jndi) {
        this.jndi = jndi;
    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(jndi);
            con = ds.getConnection();
            return con;
        } catch (NamingException ex) {
        }
        return con;
    }

    public Connection getConnection(String username, String password) throws SQLException {
        Connection con = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(jndi);
            con = ds.getConnection();
            return con;
        } catch (NamingException ex) {
        }
        return con;
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
