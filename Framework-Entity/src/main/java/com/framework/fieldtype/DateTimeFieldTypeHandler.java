package com.framework.fieldtype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class DateTimeFieldTypeHandler implements FieldTypeHandler {

    @Override
    public boolean validate(String value) {
       boolean isValidate = false;
       if (value != null) {
            if (!value.isEmpty()) {
                Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
                Matcher matcher = pattern.matcher(value);
                if (matcher.find()) {
                    isValidate = true;
                } 
            }
        }
        return isValidate;
    }
}
