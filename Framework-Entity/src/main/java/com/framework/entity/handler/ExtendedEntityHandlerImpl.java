package com.framework.entity.handler;

import com.framework.entity.annotation.ExtendedEntityConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class ExtendedEntityHandlerImpl implements ExtendedEntityHandler{

    private Map<String, FieldHandler> fieldMapHandler = new HashMap<String, FieldHandler>();
    private ExtendedEntityConfig extendedEntityConfig;
    public ExtendedEntityHandlerImpl(ExtendedEntityConfig extendedEntityConfig){
        this.extendedEntityConfig = extendedEntityConfig;
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

    public ExtendedEntityConfig getExtendedEntityConfig() {
        return this.extendedEntityConfig;
    }
}
