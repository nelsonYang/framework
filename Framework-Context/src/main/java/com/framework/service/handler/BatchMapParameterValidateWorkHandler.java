package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.field.FieldHandler;
import com.framework.field.FieldManager;
import com.framework.fieldtype.FieldTypeHandler;
import com.framework.service.api.WorkHandler;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class BatchMapParameterValidateWorkHandler implements WorkHandler {

    private WorkHandler nextHandler;
    private String[] parameters;
    private String[] minorParameters;
    private String act;

    public BatchMapParameterValidateWorkHandler(WorkHandler nextHandler, String[] parameters, String[] minorParameters, String act) {
        this.nextHandler = nextHandler;
        this.parameters = parameters;
        this.minorParameters = minorParameters;
        this.act = act;
    }

    @Override
    public void execute() {
        //验证参数
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        Map<String, String[]> bathMapParameters = invocationContext.getBatchMapParameters();
        String[] values;
        FieldTypeHandler fieldTypeHandler;
        FieldManager fieldManager = applicationContext.getFieldManager();
        FieldHandler fieldHandler;
        Map<String, FieldHandler> fieldMap = fieldManager.getFieldMap(act);
        boolean isSuccess = true;
        for (String parameter : parameters) {
            if (isSuccess) {
                fieldHandler = fieldMap.get(parameter);
                values = bathMapParameters.get(parameter);
                if (fieldHandler == null) {
                    applicationContext.setEncryptCode(9004);
                    bathMapParameters.clear();
                    applicationContext.setMsg(parameter.concat("字段不存在"));
                    isSuccess = false;
                    break;
                } else {
                    fieldTypeHandler = fieldHandler.getFieldTypeHandler();
                    for (String value : values) {
                        if (!fieldTypeHandler.validate(value)) {
                            applicationContext.setEncryptCode(9004);
                            bathMapParameters.clear();
                            applicationContext.setMsg(parameter.concat("验证不通过"));
                            isSuccess = false;
                            break;
                        }
                    }
                }
            }
        }
        //验证次要参数
        for (String parameter : minorParameters) {
            if (isSuccess) {
                fieldHandler = fieldMap.get(parameter);
                values = bathMapParameters.get(parameter);
                if (values != null) {
                    fieldTypeHandler = fieldHandler.getFieldTypeHandler();
                    for (String value : values) {
                        if (!fieldTypeHandler.validate(value)) {
                            applicationContext.setEncryptCode(9006);
                            bathMapParameters.clear();
                            applicationContext.setMsg(parameter.concat("验证不通过"));
                            isSuccess = false;
                            break;
                        }
                    }
                }
            }
        }

        if (isSuccess) {
            //验证参数
            nextHandler.execute();
        }
    }
}
