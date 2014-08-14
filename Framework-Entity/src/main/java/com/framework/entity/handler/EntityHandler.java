package com.framework.entity.handler;

import com.framework.entity.annotation.EntityConfig;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface EntityHandler<T>{
    public Class<T> getClazz();
    public EntityConfig getEntityConfig();
    public Map<String,FieldHandler> getFieldHandlerMap();
    public FieldHandler getFieldHandler(String fieldName);
    public void putFieldHandler(String fieldName,FieldHandler fieldHandler);
    
}
