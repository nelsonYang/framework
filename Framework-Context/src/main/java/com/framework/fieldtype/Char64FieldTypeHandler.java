package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char64FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 64) {
            isValidate = true;
        }
        return isValidate;
    }
}
