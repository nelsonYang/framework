package com.framework.response;

import com.framework.context.ApplicationContext;
import com.framework.entity.pojo.Entity;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.utils.ConverterUtils;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class EntityListJsonResponseWriter<T extends Entity> extends ResponseWriter {

    private final Logger logger = LogFactory.getInstance().getLogger(EntityListJsonResponseWriter.class);

    @Override
    public void toWriteJson(CryptEnum responseSecurityType, String[] returnParameters, TerminalTypeEnum terminalTypeEnum) throws Exception {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        HttpServletResponse response = applicationContext.getHttpServletResponse();
        String jsonData = "{}";
        PrintWriter printWriter = response.getWriter();
        //验证返回字段是否存在
        boolean isSuccess = true;
        List<T> tList = applicationContext.getEnityList();
        if (tList != null && !tList.isEmpty()) {
            List<Map<String, String>> dataList = ConverterUtils.toMapList(tList);
            Map<String, List<Map<String, String>>> dataListMap = new HashMap<String, List<Map<String, String>>>(2, 1);
            dataListMap.put("dataList", dataList);
            if (dataListMap != null && !dataListMap.isEmpty()) {
                Set<String> keys = dataListMap.keySet();
                List<Map<String, String>> dataMapList;
                for (String key : keys) {
                    dataMapList = dataListMap.get(key);
                    for (Map<String, String> dataMap : dataMapList) {
                        for (String parameter : returnParameters) {
                            if (!dataMap.containsKey(parameter)) {
                                applicationContext.setResultCode(9006);
                                applicationContext.setMsg(parameter.concat("字段没有设置输出"));
                                isSuccess = false;
                                break;
                            }
                        }
                    }
                }
                if (isSuccess) {
                    for (String key : keys) {
                        jsonData = JsonUtils.mapListToJsonArray(dataListMap.get(key), returnParameters, this.filterHandler);
                        jsonData = "\"" + key + "\":" + jsonData;
                    }
                    logger.debug("jsonData=".concat(jsonData));
                    if (responseSecurityType == CryptEnum.AES) {
                        jsonData = "{ " + jsonData + " }";
                        String key;
                        if (terminalTypeEnum == TerminalTypeEnum.MOBILE) {
                            String telephoneNum = applicationContext.getTelephoneNum();
                            key = applicationContext.getKey(telephoneNum);
                        } else {
                            String userUuid = applicationContext.getUserUuid();
                            key = applicationContext.getKey(userUuid);
                        }
                        if (key != null) {
                            jsonData = applicationContext.getCrypto(responseSecurityType).encrypt(jsonData, key);
                            jsonData = jsonData.replace("\n", "");
                            jsonData = jsonData.replace("\r", "");
                            jsonData = "\"" + jsonData + "\"";
                        } else {
                            applicationContext.setResultCode(9006);
                            applicationContext.setMsg("获取KEY失败");
                            jsonData = "{}";
                        }
                    } else {
                        jsonData = "{ " + jsonData + " }";
                    }
                }
            }
        }
        int resultCode = applicationContext.getResultCode();
        String msg = applicationContext.getMsg();
        StringBuilder resultJson = new StringBuilder();
        String jsonCallback = applicationContext.getJsonCallback();
        String act = applicationContext.getAct();
        if (jsonCallback != null) {
            resultJson.append(jsonCallback).append("(").append("{ \"resultCode\":").append(resultCode).append(",\"encryptCode\":").append(responseSecurityType.toString()).append(",\"act\":\"").append(act).append("\",\"msg\":\"").append(msg).append("\",\"data\":").append(jsonData).append("}").append(")");
        } else {
            resultJson.append("{ \"resultCode\":").append(resultCode).append(",\"encryptCode\":").append(responseSecurityType.toString()).append(",\"act\":\"").append(act).append("\",\"msg\":\"").append(msg).append("\",\"data\":").append(jsonData).append("}");
        }
        logger.debug("resultJson=".concat(resultJson.toString()));
        printWriter.write(resultJson.toString());
        printWriter.flush();
        printWriter.close();
    }
}
