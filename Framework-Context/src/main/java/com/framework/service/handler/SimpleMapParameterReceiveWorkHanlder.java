package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.service.api.WorkHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public class SimpleMapParameterReceiveWorkHanlder implements WorkHandler {

    private WorkHandler nextHandler;

    public SimpleMapParameterReceiveWorkHanlder(WorkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void execute() {
        //web端过来的话 跳过处理
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        HttpServletRequest request = invocationContext.getRequest();
        Map<String, String[]> batchParameterMap = request.getParameterMap();
        Map<String, String> parameterMap = new HashMap<String, String>(1, batchParameterMap.size());
        Set<Map.Entry<String, String[]>> parameterSet = batchParameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : parameterSet) {
            parameterMap.put(entry.getKey(), entry.getValue()[0]);
        }
        invocationContext.setSimpleMapParameters(parameterMap);
        nextHandler.execute();
    }
}
