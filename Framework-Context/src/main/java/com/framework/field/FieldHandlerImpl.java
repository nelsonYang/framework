package com.framework.field;

import com.framework.enumeration.FieldTypeEnum;
import com.framework.fieldtype.FieldTypeHandler;
import com.framework.fieldtype.FieldTypeManager;

/**
 *
 * @author Administrator
 */
public class FieldHandlerImpl implements FieldHandler {

    private String fieldName;
    private String defaultValue;
    private FieldTypeEnum fieldTypeEnum;

    public FieldHandlerImpl(String fieldName, FieldTypeEnum fieldTypeEnum, String defaultValue) {
        this.fieldName = fieldName;
        this.defaultValue = defaultValue;
        this.fieldTypeEnum = fieldTypeEnum;
    }

    public FieldHandlerImpl(String fieldName, FieldTypeEnum fieldTypeEnum) {
        this.fieldName = fieldName;
        this.fieldTypeEnum = fieldTypeEnum;
    }

    @Override
    public String getFieldName() {
      return this.fieldName;
    }

    @Override
    public FieldTypeHandler getFieldTypeHandler() {
      return FieldTypeManager.getInstance().getFieldTypeHandler(fieldTypeEnum);
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
