package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char16FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
         boolean isValidate = false;
        if (value != null && value.length() <= 16) {
            isValidate = true;
        }
        return isValidate;
    }
}
