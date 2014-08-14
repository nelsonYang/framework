package com.framework.annotation;

import com.framework.enumeration.FieldTypeEnum;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Administrator
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String fieldName();
    FieldTypeEnum fieldType();
    String defaultValue() default "";
    String description() default "";
}
