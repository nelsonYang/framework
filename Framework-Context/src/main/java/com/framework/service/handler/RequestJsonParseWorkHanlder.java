package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.service.api.WorkHandler;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class RequestJsonParseWorkHanlder implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(RequestJsonParseWorkHanlder.class);
    private WorkHandler nextHandler;

    public RequestJsonParseWorkHanlder(WorkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void execute() {
        //web端过来的话 跳过处理
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        String requestJson = invocationContext.getRequestJson();
        logger.debug("requestJson=".concat(requestJson));
        requestJson = requestJson.replace("\n", "");
        requestJson = requestJson.replace("\r", "");
        if (requestJson != null) {
            //解析json
            Map<String, String> requestMap = JsonUtils.parseJsonToMap(requestJson);
            if (requestMap != null) {
                //如果只有request 没有data就需要验证
                if (applicationContext.getSign() != null) {
                    requestMap.put("sign", applicationContext.getSign());
                }
                if (requestMap.get("data") == null) {
                    requestMap.put("data", requestJson);
                }
                invocationContext.setRequestMap(requestMap);
                nextHandler.execute();
            } else {
                applicationContext.setResultCode(9020);
                applicationContext.setMsg("JSON无法解析");
            }
        } else {
            applicationContext.setResultCode(9004);
            applicationContext.setMsg("参数错误");
        }
    }
}
