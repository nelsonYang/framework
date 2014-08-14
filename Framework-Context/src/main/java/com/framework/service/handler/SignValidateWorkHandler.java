package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.crypto.ICrypto;
import com.framework.enumeration.CryptEnum;
import com.framework.service.api.WorkHandler;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class SignValidateWorkHandler implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(SignValidateWorkHandler.class);
    private WorkHandler nextHandler;

    public SignValidateWorkHandler(WorkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        String sign = invocationContext.getSign();
        String requestJson = invocationContext.getRequestJson();
        ICrypto md5EnCryption = applicationContext.getCrypto(CryptEnum.MD5);
        String md5Result = md5EnCryption.encrypt(requestJson, null);
        logger.debug("md5Result=".concat(md5Result));
        logger.debug("sign=".concat(sign));
        if (sign != null) {
            if (md5Result.equals(sign.toUpperCase())) {
                //获取uuid手机号
                Map<String, String> parameterMap = JsonUtils.parseJsonToMap(requestJson);
                logger.debug("parameterMap =".concat(parameterMap.toString()));
                String telephoneNum = parameterMap.get("uuid");
                if (telephoneNum != null) {
                    applicationContext.setTelephoneNum(telephoneNum);
                }
                String userName = parameterMap.get("userName");
                if (userName != null) {
                    applicationContext.setUserName(userName);
                }
                nextHandler.execute();
            } else {
                applicationContext.setEncryptCode(9019);
                applicationContext.setMsg("签名验证失败");
            }
        } else {
            applicationContext.setEncryptCode(9019);
            applicationContext.setMsg("签名验证失败");
        }

    }
}
