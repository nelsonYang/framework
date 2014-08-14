package com.framework.entity.handler;

import com.framework.entity.annotation.FieldConfig;

/**
 *
 * @author nelson
 */
public interface FieldHandler {

    public FieldConfig getFieldConfig();

    public boolean validate(String fieldName, String value);

    public void format(String fieldName, String value, String format);
}
