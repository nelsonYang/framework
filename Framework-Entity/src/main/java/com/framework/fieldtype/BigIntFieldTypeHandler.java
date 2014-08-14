package com.framework.fieldtype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class BigIntFieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        //正则匹配
        if (value != null) {
            Pattern pattern = Pattern.compile("^-?\\d{1,19}$");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                long longValue = Long.parseLong(value);
                if (longValue > -9223372036854775808l && longValue < 9223372036854775807l) {
                    isValidate = true;
                }
            }
        }
        return isValidate;
    }
}
