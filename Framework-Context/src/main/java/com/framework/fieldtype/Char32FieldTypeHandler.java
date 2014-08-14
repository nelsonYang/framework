package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char32FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
         boolean isValidate = false;
        if (value != null && value.length() <= 32) {
            isValidate = true;
        }
        return isValidate;
    }
}
