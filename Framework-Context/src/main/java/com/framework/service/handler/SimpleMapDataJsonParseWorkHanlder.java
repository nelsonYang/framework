package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.service.api.WorkHandler;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class SimpleMapDataJsonParseWorkHanlder implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(SimpleMapDataJsonParseWorkHanlder.class);
    private WorkHandler nextHandler;
    private String[] parameters;
    private String[] minorParameters;

    public SimpleMapDataJsonParseWorkHanlder(WorkHandler nextHandler, String[] parameters, String[] minorParameters) {
        this.nextHandler = nextHandler;
        this.parameters = parameters;
        this.minorParameters = minorParameters;
    }

    @Override
    public void execute() {
        //web端过来的话 跳过处理
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        Map<String, String> requestMap = invocationContext.getRequestMap();
        String jsonData = requestMap.get("data");
        logger.debug("jsonData=".concat(jsonData));
        Map<String, String> dataMap = JsonUtils.parseJsonToMap(jsonData);
        Map<String, String> simpleMap = new HashMap<String, String>();
        String value;
        boolean isSuccess = true;
        for (String parameter : parameters) {
            if (dataMap.containsKey(parameter)) {
                value = dataMap.get(parameter);
                simpleMap.put(parameter, value);
            } else {
                if (requestMap.containsKey(parameter)) {
                    value = requestMap.get(parameter);
                    simpleMap.put(parameter, value);
                } else {
                    applicationContext.setEncryptCode(9004);
                    simpleMap.clear();
                    applicationContext.setMsg(parameter.concat("参数没有指定"));
                    isSuccess = false;
                    break;
                }
            }
        }
        for (String minorParameter : minorParameters) {
            if (dataMap.containsKey(minorParameter)) {
                value = dataMap.get(minorParameter);
                simpleMap.put(minorParameter, value);
            } else {
                if (requestMap.containsKey(minorParameter)) {
                    value = requestMap.get(minorParameter);
                    simpleMap.put(minorParameter, value);
                }
            }
        }
        //requestMap 加入simpleMap
        if (isSuccess) {
            Set<String> keys = requestMap.keySet();
            for (String key : keys) {
                if (!"data".equals(key)) {
                    simpleMap.put(key, requestMap.get(key));
                }
            }
            invocationContext.setSimpleMapParameters(simpleMap);
            nextHandler.execute();
        }
    }
}
