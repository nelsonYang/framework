package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char8FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 8) {
            isValidate = true;
        }
        return isValidate;
    }
}
