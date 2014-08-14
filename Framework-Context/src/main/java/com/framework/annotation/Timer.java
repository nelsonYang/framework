package com.framework.annotation;

import com.framework.enumeration.TimerEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author nelson
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {
    public TimerEnum timerType();
    public String initialTime() default "";
    public long deltTime() default 24*60*60*1000l;
}
