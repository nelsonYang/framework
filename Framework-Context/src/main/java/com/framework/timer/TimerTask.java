package com.framework.timer;

import com.framework.utils.DateTimeUtils;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author nelson
 */
public final class TimerTask {

    private static TimerTask timerTask = new TimerTask();
    private ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(3);

    public static TimerTask getInstance() {
        return timerTask;
    }

    public void addFixedTimeTask(Runnable task, String initalTime, long delay, TimeUnit timeUnit) {
        scheduExec.schedule(task, delay, timeUnit);
    }

    public void addFixedRateTask(Runnable task, String initalTime, long period, TimeUnit timeUnit) {
        long initDelay;
        if (initalTime != null && !initalTime.isEmpty()) {
            initDelay = DateTimeUtils.getTimeMillis(initalTime) - System.currentTimeMillis();
            initDelay = initDelay > 0 ? initDelay : period + initDelay;
        } else {
            initDelay = 0;
        }
        scheduExec.scheduleAtFixedRate(task, initDelay, period, timeUnit);
    }
}
