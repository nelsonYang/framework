package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char128FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 128) {
            isValidate = true;
        }
        return isValidate;
    }
}
