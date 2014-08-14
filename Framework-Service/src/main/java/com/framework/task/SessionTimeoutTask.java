package com.framework.task;

import com.framework.annotation.Timer;
import com.framework.context.ApplicationContext;
import com.framework.enumeration.TimerEnum;
import com.framework.spi.provider.SessionManager;
import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;


/**
 *
 * @author nelson
 */
@Timer(
        timerType = TimerEnum.FIXED_RATE,
        deltTime = 1 * 60 * 1000l)
public class SessionTimeoutTask implements Runnable {

    private final Logger logger = LogFactory.getInstance().getLogger(SessionTimeoutTask.class);

    @Override
    public void run() {
        logger.info("检查session是否失效的任务开始执行........");
        try {
            ApplicationContext applicationContext = ApplicationContext.CTX;
            SessionManager sessionManager = applicationContext.getSessionManager();
            long sessionTimeout = applicationContext.getSessionTimeout();
            sessionManager.removeTimeout(sessionTimeout);
        } catch (Exception ex) {
            logger.error("SessionTimeoutTask", ex);
        }
    }
}
