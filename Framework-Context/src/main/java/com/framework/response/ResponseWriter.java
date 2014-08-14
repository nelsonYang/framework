package com.framework.response;

import com.framework.context.ApplicationContext;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FilterTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.utils.filter.FilterHandler;
import com.frameworkLog.factory.LogFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public abstract class ResponseWriter {

    private final Logger logger = LogFactory.getInstance().getLogger(ResponseWriter.class);
    protected FilterHandler filterHandler;

    public ResponseWriter() {
        filterHandler = ApplicationContext.CTX.getFilterHandler(FilterTypeEnum.ESCAPE);
    }
  
    public abstract void  toWriteJson(CryptEnum responseSecurityType, String[] returnParameters, TerminalTypeEnum terminalTypeEnum) throws Exception;
    
    public void toWrite(String json) {
        logger.debug("resultJson=".concat(json));
        ApplicationContext applicationContext = ApplicationContext.CTX;
        HttpServletResponse response = applicationContext.getHttpServletResponse();
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(json);
            printWriter.flush();
        } catch (IOException ex) {
            logger.error("toWrite 输出josn的出现异常", ex);
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    public void toWriteErrorMsg() {
        String act = ApplicationContext.CTX.getAct();
        String json = "{\"resultCode\":\"9003\",\"msg\":\"未知错误\",\"data\":{},\"encryptType\":\"0\"" + ",\"act\":\"" + act + "\"}";
        this.toWrite(json);
    }

    public void toWriteErrorMsg(int resultCode, String msg) {
        String act = ApplicationContext.CTX.getAct();
        String json = "{\"resultCode\":\"" + resultCode + "\",\"msg\":\"" + msg + "\",\"data\":{},\"encryptType\":\"0\"" + ",\"act\":\"" + act + "\"}";
        this.toWrite(json);
    }

    public void toWriteErrorMsg(String content) {
        String act = ApplicationContext.CTX.getAct();
        String json = "{\"resultCode\":\"9003\",\"msg\":\"\",\"data\":{},\"encryptType\":\"0\",\"errorMsg\":".concat(content).concat("\"") + ",\"act\":\"" + act + "\"}";;
        this.toWrite(json);
    }

    public void toWriteErrorMsg(Throwable t) {
        String act = ApplicationContext.CTX.getAct();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String json = "{\"resultCode\":\"9003\",\"msg\":\"\",\"data\":{},\"encryptType\":\"0\",\"errorMsg\":".concat(sw.toString()).concat("\"") + ",\"act\":\"" + act + "\"}";;
        this.toWrite(json);
    }

    public void toWriteServiceErrorMsg() {
        String act = ApplicationContext.CTX.getAct();
        String json = "{\"resultCode\":\"9005\",\"msg\":\"接口已经失效或者不存在\",\"data\":{},\"encryptType\":\"0\"" + ",\"act\":\"" + act + "\"}";
        this.toWrite(json);
    }

    public void close() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        HttpServletResponse response = applicationContext.getHttpServletResponse();
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
        } catch (IOException ex) {
            //log
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
