package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.response.ResponseWriter;
import com.framework.service.api.WorkHandler;
import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class JsonWriteWorkHandler implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(JsonWriteWorkHandler.class);
    private WorkHandler workHandler;
    private CryptEnum responseSecurityType;
    private String[] returnParameters;
    private TerminalTypeEnum terminalTypeEnum;
    private ResponseTypeEnum responseTypeEnum;

    public JsonWriteWorkHandler(WorkHandler workHandler, CryptEnum responseSecurityType, TerminalTypeEnum terminalTypeEnum, ResponseTypeEnum responseTypeEnum, String[] returnParameters) {
        this.workHandler = workHandler;
        this.responseSecurityType = responseSecurityType;
        this.terminalTypeEnum = terminalTypeEnum;
        this.responseTypeEnum = responseTypeEnum;
        this.returnParameters = returnParameters;
    }

    @Override
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        ResponseWriter responseWriter = applicationContext.getResponseWriter(this.responseTypeEnum);
        try {
            //输出数据
            workHandler.execute();
            responseWriter.toWriteJson(responseSecurityType, returnParameters, terminalTypeEnum);
        } catch (Exception ex) {
            logger.error("execute 出现异常", ex);
            // responseWriter.toWriteErrorMsg(ex);
            if (ex instanceof RollBackException) {
                RollBackException exception = (RollBackException) ex;
                int errorCode = exception.getErrorCode();
                String message = exception.getMessage();
                if (errorCode != -1) {
                    responseWriter.toWriteErrorMsg(errorCode, message);
                } else {
                    responseWriter.toWriteErrorMsg();
                }
            } else {
                responseWriter.toWriteErrorMsg();
            }

        } finally {
            if (responseWriter != null) {
                responseWriter.close();
            }
        }
    }
}
