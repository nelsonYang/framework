package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.service.api.WorkHandler;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public class BatchMapParameterReceiveWorkHanlder implements WorkHandler {

    private WorkHandler nextHandler;

    public BatchMapParameterReceiveWorkHanlder(WorkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void execute() {
        //web端过来的话 跳过处理
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        HttpServletRequest request = invocationContext.getRequest();
        Map<String, String[]> batchParameterMap = request.getParameterMap();
        invocationContext.setBatchMapParameters(batchParameterMap);
        nextHandler.execute();
    }
}
