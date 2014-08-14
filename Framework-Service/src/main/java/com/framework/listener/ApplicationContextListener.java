package com.framework.listener;

import com.framework.context.ApplicationContextBuilder;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Administrator
 */
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new ApplicationContextBuilder().baseBuild();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
     
    }
    
}