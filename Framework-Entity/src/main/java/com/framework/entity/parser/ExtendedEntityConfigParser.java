package com.framework.entity.parser;

import com.framework.entity.annotation.ExtendedEntityConfig;
import com.framework.entity.annotation.FieldConfig;
import com.framework.entity.builder.ExtendedEntityContextBuilder;
import com.framework.entity.handler.ExtendedEntityHandler;
import com.framework.entity.handler.ExtendedEntityHandlerImpl;
import com.framework.entity.handler.FieldHandler;
import com.framework.entity.handler.FieldHandlerImpl;
import com.frameworkLog.factory.LogFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public final class ExtendedEntityConfigParser {

    private Logger logger = LogFactory.getInstance().getLogger(ExtendedEntityConfigParser.class);

    public void parse(List<String> clazzList, ExtendedEntityContextBuilder extendedEntityContextBuilder) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            for (String className : clazzList) {

                Class clazz = classLoader.loadClass(className);
                if (clazz.isAnnotationPresent(ExtendedEntityConfig.class)) {
                    ExtendedEntityConfig extendedEntityConfig = (ExtendedEntityConfig) clazz.getAnnotation(ExtendedEntityConfig.class);
                    ExtendedEntityHandler extendedEntityHandler = new ExtendedEntityHandlerImpl(extendedEntityConfig);
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        int modifier = field.getModifiers();
                        if (!Modifier.isStatic(modifier)) {
                            if (field.isAnnotationPresent(FieldConfig.class)) {
                                FieldConfig fieldConfig = (FieldConfig) field.getAnnotation(FieldConfig.class);
                                FieldHandler fieldHandler = new FieldHandlerImpl(fieldConfig);
                                extendedEntityHandler.putFieldHandler(fieldConfig.fieldName(), fieldHandler);
                            }
                        }
                    }
                    extendedEntityContextBuilder.putExtendedEntityHandler(extendedEntityConfig.extendedEntityName(), extendedEntityHandler);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("parser exception", ex);
        }
    }
}
