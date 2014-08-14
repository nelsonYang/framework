package com.framework.entity.parser;

import com.framework.entity.annotation.EntityConfig;
import com.framework.entity.annotation.FieldConfig;
import com.framework.entity.builder.EntityContextBuilder;
import com.framework.entity.handler.EntityHandler;
import com.framework.entity.handler.EntityHandlerImpl;
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
public final class EntityConfigParser<T> {

    private Logger logger = LogFactory.getInstance().getLogger(EntityConfigParser.class);

    public void parse(List<String> clazzList, EntityContextBuilder entityContextBuilder) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            for (String className : clazzList) {
                Class<T> clazz = (Class<T>) classLoader.loadClass(className);
                if (clazz.isAnnotationPresent(EntityConfig.class)) {
                    EntityConfig entityConfig = (EntityConfig) clazz.getAnnotation(EntityConfig.class);
                    EntityHandler entityHandler = new EntityHandlerImpl(entityConfig, clazz);
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        int modifier = field.getModifiers();
                        if (!Modifier.isStatic(modifier)) {
                            if (field.isAnnotationPresent(FieldConfig.class)) {
                                FieldConfig fieldConfig = (FieldConfig) field.getAnnotation(FieldConfig.class);
                                FieldHandler fieldHandler = new FieldHandlerImpl(fieldConfig);
                                entityHandler.putFieldHandler(fieldConfig.fieldName(), fieldHandler);
                            }
                        }
                    }
                    entityContextBuilder.putEntityHandler(entityConfig.entityName(), entityHandler);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("parser exception", ex);
        }

    }
}
