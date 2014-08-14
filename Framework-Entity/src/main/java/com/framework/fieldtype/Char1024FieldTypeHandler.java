package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char1024FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 1024) {
            isValidate = true;
        }
        return isValidate;
    }
}
