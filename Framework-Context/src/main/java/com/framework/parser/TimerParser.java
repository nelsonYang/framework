package com.framework.parser;

import com.framework.annotation.Timer;
import com.framework.enumeration.TimerEnum;
import com.framework.service.api.Parser;
import com.framework.timer.TimerTask;
import com.frameworkLog.factory.LogFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public final class TimerParser implements Parser {

    private final Logger logger = LogFactory.getInstance().getLogger(TimerParser.class);

    @Override
    public void parse(String[] packageNames) {
        logger.info("开始解析TimerParser.....");
        List<String> classNameList = new ClassParser().parse(packageNames);
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Timer timer;
        String intialTime;
        long deltTime;
        Runnable task;
        TimerEnum timerType;
        TimerTask timerTask = TimerTask.getInstance();
        for (String className : classNameList) {
            try {
                Class clazz = classLoader.loadClass(className);
                if (clazz.isAnnotationPresent(Timer.class) && Runnable.class.isAssignableFrom(clazz)) {
                    timer = (Timer) clazz.getAnnotation(Timer.class);
                    intialTime = timer.initialTime();
                    timerType = timer.timerType();
                    deltTime = timer.deltTime();
                    task = (Runnable) clazz.newInstance();
                    if (timerType == TimerEnum.FIXED_RATE) {
                        timerTask.addFixedRateTask(task, intialTime, deltTime, TimeUnit.MILLISECONDS);
                    } else {
                        timerTask.addFixedTimeTask(task, intialTime, deltTime, TimeUnit.MILLISECONDS);
                    }
                }
            } catch (Exception ex) {
                logger.error("parse 解析TimerParser出现异常", ex);
            }
        }
    }
}
