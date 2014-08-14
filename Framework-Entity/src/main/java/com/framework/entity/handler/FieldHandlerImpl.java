package com.framework.entity.handler;

import com.framework.entity.annotation.FieldConfig;

/**
 *
 * @author nelson
 */
public class FieldHandlerImpl implements FieldHandler{
    private FieldConfig fieldConfig;
    public FieldHandlerImpl(FieldConfig fieldConfig){
        this.fieldConfig = fieldConfig;
    }

    public FieldConfig getFieldConfig() {
       return this.fieldConfig;
    }

    public boolean validate(String fieldName, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void format(String fieldName, String value, String format) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
