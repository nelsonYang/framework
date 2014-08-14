package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char512FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 512) {
            isValidate = true;
        }
        return isValidate;
    }
}
