package com.framework.task;

import com.framework.annotation.Timer;
import com.framework.context.ApplicationContext;
import com.framework.enumeration.TimerEnum;
import com.framework.spi.provider.WebSessionManager;
import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;


/**
 *
 * @author nelson
 */
@Timer(
        timerType = TimerEnum.FIXED_RATE,
        deltTime = 1*60*1000l)
public class WebSessionTimeoutTask implements Runnable {

    private final Logger logger = LogFactory.getInstance().getLogger(WebSessionTimeoutTask.class);

    @Override
    public void run() {
        logger.info("检查websession是否失效的任务开始执行........");
        try {
            ApplicationContext applicationContext = ApplicationContext.CTX;
            WebSessionManager webSessionManager = applicationContext.getWebSessionManager();
            long sessionTimeout = applicationContext.getSessionTimeout();
            webSessionManager.removeTimeout(sessionTimeout);
        } catch (Exception ex) {
               logger.error("SessionTimeoutTask",ex);
        }
    }
}
