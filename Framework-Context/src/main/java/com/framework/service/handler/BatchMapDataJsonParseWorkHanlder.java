package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.service.api.WorkHandler;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class BatchMapDataJsonParseWorkHanlder implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(BatchMapDataJsonParseWorkHanlder.class);
    private WorkHandler nextHandler;
    private String[] parameters;
    private String[] minorParameters;

    public BatchMapDataJsonParseWorkHanlder(WorkHandler nextHandler, String[] parameters, String[] minorParameters) {
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
        String jsonArrayData = requestMap.get("data");
        logger.debug("jsonArrayData = ".concat(jsonArrayData));
        List<Map<String, String>> dataMapList = null;
        Map<String, String> parameterMap;
        if (jsonArrayData.startsWith("[") && jsonArrayData.endsWith("]")) {
            // jsonArrayData = "[" + jsonArrayData + "]";
            dataMapList = JsonUtils.parseJsonArrayToMap(jsonArrayData);
        } else if (jsonArrayData.startsWith("{") && jsonArrayData.endsWith("}")) {
            dataMapList = new ArrayList<Map<String, String>>();
            Map<String, String> jsonObjectMap = JsonUtils.parseJsonToMap(jsonArrayData);
            Set<String> keySet = jsonObjectMap.keySet();
            for (String key : keySet) {
                jsonArrayData = jsonObjectMap.get(key);
                if (jsonArrayData.startsWith("[") && jsonArrayData.endsWith("]")) {
                    List<Map<String, String>> tempDataMapList = JsonUtils.parseJsonArrayToMap(jsonArrayData);
                    for (Map<String, String> tempMap : tempDataMapList) {
                        dataMapList.add(tempMap);
                    }
                } else {
                    parameterMap = new HashMap<String, String>(2, 1);
                    parameterMap.put(key, jsonArrayData);
                    dataMapList.add(parameterMap);
                }
            }
        } else {
            jsonArrayData = "[" + jsonArrayData + "]";
            dataMapList = JsonUtils.parseJsonArrayToMap(jsonArrayData);
        }
        Map<String, String[]> batchMap = new HashMap<String, String[]>();
        //解析json

        List<String> valueList = new ArrayList<String>(0);
        String value;
        boolean isSuccess = true;
        for (String parameter : parameters) {
            valueList.clear();
            for (Map<String, String> dataMap : dataMapList) {
                if (dataMap.containsKey(parameter)) {
                    value = dataMap.get(parameter);
                    valueList.add(value);
                } else {
                    if (requestMap.containsKey(parameter)) {
                        value = requestMap.get(parameter);
                        valueList.add(value);
                    }
                }
            }
            if (valueList.size() > 0) {
                batchMap.put(parameter, valueList.toArray(new String[]{}));
            } else {
                applicationContext.setEncryptCode(9004);
                applicationContext.setMsg(parameter.concat("参数没有传入"));
                batchMap.clear();
                isSuccess = false;
                break;
            }
        }
        for (String minorParameter : minorParameters) {
            valueList.clear();
            for (Map<String, String> dataMap : dataMapList) {
                if (dataMap.containsKey(minorParameter)) {
                    value = dataMap.get(minorParameter);
                    valueList.add(value);
                } else {
                    if (requestMap.containsKey(minorParameter)) {
                        value = requestMap.get(minorParameter);
                        valueList.add(value);
                    }
                }
            }
            if (valueList.size() > 0) {
                batchMap.put(minorParameter, valueList.toArray(new String[]{}));
            }
        }
        //requestMap 加入simpleMap
        if (isSuccess) {
            Set<String> keys = requestMap.keySet();
            for (String key : keys) {
                if (!"data".equals(key)) {
                    batchMap.put(key, new String[]{requestMap.get(key)});
                }
            }
            invocationContext.setBatchMapParameters(batchMap);
            nextHandler.execute();
        }
    }
}
