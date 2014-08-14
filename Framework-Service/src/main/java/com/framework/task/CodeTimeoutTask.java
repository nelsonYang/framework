package com.framework.task;

import com.framework.annotation.Timer;
import com.framework.context.ApplicationContext;
import com.framework.enumeration.TimerEnum;
import com.framework.spi.provider.CodeManager;
import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@Timer(
        timerType = TimerEnum.FIXED_RATE,
        deltTime = 1*60*1000l)
public class CodeTimeoutTask implements Runnable {

    private final Logger logger = LogFactory.getInstance().getLogger(CodeTimeoutTask.class);

    @Override
    public void run() {
        logger.info("检查验证码是否失效的任务开始执行........");
        try {
            ApplicationContext applicationContext = ApplicationContext.CTX;
            CodeManager codeManager = applicationContext.getCodeManager();
            long codeTime = applicationContext.getCodeTimeout();
            codeManager.removeTimeout(codeTime);
        } catch (Exception ex) {
            logger.error("CodeTimeoutTask",ex);
        }
    }
}
