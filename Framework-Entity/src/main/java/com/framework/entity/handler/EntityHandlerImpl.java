package com.framework.entity.handler;

import com.framework.entity.annotation.EntityConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class EntityHandlerImpl<T> implements EntityHandler<T>{
    private Map<String,FieldHandler> fieldMapHandler = new HashMap<String,FieldHandler>();
    private EntityConfig entityConfig;
    private Class<T> clazz;
    public EntityHandlerImpl(EntityConfig entityConfig,Class<T> clazz){
        this.entityConfig = entityConfig;
        this.clazz = clazz;
    }
    public EntityConfig getEntityConfig() {
        return this.entityConfig;
    }

    public Map<String, FieldHandler> getFieldHandlerMap() {
        return this.fieldMapHandler;
    }

    public FieldHandler getFieldHandler(String fieldName) {
        return this.fieldMapHandler.get(fieldName);
    }

    public void putFieldHandler(String fieldName, FieldHandler fieldHandler) {
        this.fieldMapHandler.put(fieldName, fieldHandler);
    }

    public Class<T> getClazz() {
        return this.clazz;
     }
    
}
