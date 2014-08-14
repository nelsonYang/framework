package com.framework.fieldtype;

import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;



/**
 *
 * @author Administrator
 */
public class DoubleFieldTypeHandler implements FieldTypeHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(DoubleFieldTypeHandler.class);

    @Override
    public boolean validate(String value) {
        boolean isValidate = false;
        if (value != null) {
            try {
                Double.parseDouble(value);
                isValidate = true;
            } catch (Exception ex) {
                logger.warn("验证double类型出现异常", ex);
            }
        }
        return isValidate;
    }
}
