package com.framework.entity.handler;

import com.framework.entity.annotation.ExtendedEntityConfig;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface ExtendedEntityHandler {

    public ExtendedEntityConfig getExtendedEntityConfig();

    public Map<String, FieldHandler> getFieldHandlerMap();

    public FieldHandler getFieldHandler(String fieldName);
    
    public void putFieldHandler(String fieldName,FieldHandler fieldHandler);
}
