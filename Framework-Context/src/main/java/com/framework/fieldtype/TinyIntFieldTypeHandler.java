package com.framework.fieldtype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class TinyIntFieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null) {
            //正则匹配
            Pattern pattern = Pattern.compile("^-?\\d{1,3}$");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                int intValue = Integer.parseInt(value);
                if (intValue > -128 && intValue < 127) {
                    isValidate = true;
                }
            }
        }
        return isValidate;
    }
}
