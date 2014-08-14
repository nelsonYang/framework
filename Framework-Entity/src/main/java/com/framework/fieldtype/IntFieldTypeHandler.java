package com.framework.fieldtype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class IntFieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        //正则匹配
        if (value != null) {
            Pattern pattern = Pattern.compile("^-?\\d{1,10}$");
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                int intValue = Integer.parseInt(value);
                if (intValue > -2147483648 && intValue < 2147483647) {
                    isValidate = true;
                }
            }
        }
        return isValidate;
    }
}
