package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char24FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
         boolean isValidate = false;
        if (value != null && value.length() <= 24) {
            isValidate = true;
        }
        return isValidate;
    }
}
