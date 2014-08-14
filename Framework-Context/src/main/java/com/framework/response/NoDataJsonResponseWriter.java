package com.framework.response;

import com.framework.context.ApplicationContext;
import com.framework.entity.pojo.Entity;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.frameworkLog.factory.LogFactory;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class NoDataJsonResponseWriter<T extends Entity> extends ResponseWriter {

    private final Logger logger = LogFactory.getInstance().getLogger(NoDataJsonResponseWriter.class);

    @Override
    public void toWriteJson(CryptEnum responseSecurityType, String[] returnParameters, TerminalTypeEnum terminalTypeEnum) throws Exception {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        HttpServletResponse response = applicationContext.getHttpServletResponse();
        String jsonData = "{}";
        PrintWriter printWriter = response.getWriter();
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
