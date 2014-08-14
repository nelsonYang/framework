package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char36FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 36) {
            isValidate = true;
        }
        return isValidate;
    }
}
