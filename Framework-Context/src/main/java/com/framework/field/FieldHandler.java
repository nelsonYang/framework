package com.framework.field;

import com.framework.fieldtype.FieldTypeHandler;


/**
 *
 * @author Administrator
 */
public interface FieldHandler {
    public String getFieldName();
    public FieldTypeHandler getFieldTypeHandler();
    public String getDefaultValue();
}
