package com.framework.fieldtype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class SmallIntFieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null) {
            Pattern pattern = Pattern.compile("^-?\\d{1,5}$");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                int intValue = Integer.parseInt(value);
                if (intValue > -32768 && intValue < 32767) {
                    isValidate = true;
                }
            }
        }
        return isValidate;
    }
}
