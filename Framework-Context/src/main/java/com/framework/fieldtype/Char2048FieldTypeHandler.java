package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char2048FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 2048) {
            isValidate = true;
        }
        return isValidate;
    }
}
