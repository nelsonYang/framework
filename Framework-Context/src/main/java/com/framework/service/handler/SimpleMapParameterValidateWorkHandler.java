package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.field.FieldHandler;
import com.framework.field.FieldManager;
import com.framework.fieldtype.FieldTypeHandler;
import com.framework.service.api.WorkHandler;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class SimpleMapParameterValidateWorkHandler implements WorkHandler {

    private WorkHandler nextHandler;
    private String[] parameters;
    private String[] minorParameters;
    private String act;

    public SimpleMapParameterValidateWorkHandler(WorkHandler nextHandler, String[] parameters, String[] minorParameters, String act) {
        this.nextHandler = nextHandler;
        this.parameters = parameters;
        this.minorParameters = minorParameters;
        this.act = act;

    }

    @Override
    public void execute() {
        //验证参数
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> simpleMap = applicationContext.getSimpleMapParameters();
        FieldManager fieldManager = applicationContext.getFieldManager();
        String value;
        FieldTypeHandler fieldTypeHandler;
        FieldHandler fieldHandler;
        Map<String, FieldHandler> fieldMap = fieldManager.getFieldMap(act);
        boolean isSuccess = true;
        for (String parameter : parameters) {
            fieldHandler = fieldMap.get(parameter);
            value = simpleMap.get(parameter);
            if (fieldHandler == null) {
                applicationContext.setEncryptCode(9004);
                applicationContext.setMsg(parameter.concat("字段不存在"));
                simpleMap.clear();
                isSuccess = false;
                break;
            } else {
                fieldTypeHandler = fieldHandler.getFieldTypeHandler();
                if (!fieldTypeHandler.validate(value)) {
                    applicationContext.setEncryptCode(9004);
                    applicationContext.setMsg(parameter.concat("验证不通过"));
                    simpleMap.clear();
                    isSuccess = false;
                    break;
                }
            }
        }
        //验证次要参数
        for (String parameter : minorParameters) {
            fieldHandler = fieldMap.get(parameter);
            value = simpleMap.get(parameter);
            if (value != null) {
                fieldTypeHandler = fieldHandler.getFieldTypeHandler();
                if (!fieldTypeHandler.validate(value)) {
                    applicationContext.setEncryptCode(9006);
                    applicationContext.setMsg(parameter.concat("验证不通过"));
                    simpleMap.clear();
                    isSuccess = false;
                    break;
                }
            }
        }
        if (isSuccess) {
            nextHandler.execute();
        }
    }
}
