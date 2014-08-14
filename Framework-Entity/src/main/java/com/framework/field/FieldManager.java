package com.framework.field;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public final class FieldManager {

    private static FieldManager fieldManager;
    private final Map<String, Map<String, FieldHandler>> fieldHandlerMap = new HashMap<String, Map<String, FieldHandler>>();

    public static synchronized FieldManager getInstance() {
        if (fieldManager == null) {
            fieldManager = new FieldManager();
        }
        return fieldManager;
    }

    public void regiester(String act, Map<String, FieldHandler> fieldMap) {
        this.fieldHandlerMap.put(act, fieldMap);
    }
    
    public Map<String,FieldHandler> getFieldMap(String act){
        return this.fieldHandlerMap.get(act);
    }

    public void register(String act, String fieldName, FieldHandler fieldHandler) {
        Map<String, FieldHandler> fieldMap = this.fieldHandlerMap.get(act);
        if (fieldMap == null) {
            fieldMap = new HashMap<String, FieldHandler>(2, 1);
            fieldMap.put(fieldName, fieldHandler);
            this.fieldHandlerMap.put(act, fieldMap);
        } else {
            fieldMap.put(fieldName, fieldHandler);
        }
    }
}
