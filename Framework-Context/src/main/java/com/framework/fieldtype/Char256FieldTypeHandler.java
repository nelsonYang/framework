package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char256FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null && value.length() <= 256) {
            isValidate = true;
        }
        return isValidate;
    }
}
