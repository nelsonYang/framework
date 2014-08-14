package com.frameworkLog.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 *
 * @author Administrator
 */
public class LogFactory {

    private static final LogFactory loggerFactory = new LogFactory();

    public static LogFactory getInstance() {
        return loggerFactory;
    }

    public Logger getLogger(Class clazz) {
        
        return LoggerFactory.getLogger(clazz);
    }

    public Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
}
