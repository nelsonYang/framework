package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char65535FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 65535) {
            isValidate = true;
        }
        return isValidate;
    }
}
