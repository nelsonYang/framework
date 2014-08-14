package com.framework.fieldtype;

/**
 *
 * @author Administrator
 */
public class Char11FieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
         boolean isValidate = false;
        if (value != null && value.length() == 11) {
            isValidate = true;
        }
        return isValidate;
    }
}
