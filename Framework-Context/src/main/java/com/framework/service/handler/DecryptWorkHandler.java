package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.crypto.ICrypto;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.WorkHandler;
import com.frameworkLog.factory.LogFactory;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class DecryptWorkHandler implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(DecryptWorkHandler.class);
    private WorkHandler nextHandler;
    private CryptEnum requestSecurityType;
    private TerminalTypeEnum terminalTypeEnum;

    public DecryptWorkHandler(WorkHandler nextHandler, CryptEnum requestSecurityType, TerminalTypeEnum terminalTypeEnum) {
        this.nextHandler = nextHandler;
        this.requestSecurityType = requestSecurityType;
        this.terminalTypeEnum = terminalTypeEnum;
    }

    @Override
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        Map<String, String> requestMap = invocationContext.getRequestMap();
        String data = requestMap.get("data");
        String key;
        if (terminalTypeEnum == TerminalTypeEnum.MOBILE) {
            String telephoneNum = applicationContext.getTelephoneNum();
            key = applicationContext.getKey(telephoneNum);
        } else {
            String userUuid = applicationContext.getUserUuid();
            key = applicationContext.getKey(userUuid);
        }
        if (key != null && data != null) {
            ICrypto icrypto = applicationContext.getCrypto(this.requestSecurityType);
            String jsonData = icrypto.decrypt(data, key);
            if (jsonData != null) {
                logger.debug("decrypt jsonData = ".concat(jsonData));
                requestMap.put("data", jsonData);
                nextHandler.execute();
            } else {
                applicationContext.setResultCode(9021);
                applicationContext.setMsg("解密失败");
            }
        } else {
            applicationContext.setResultCode(9006);
            applicationContext.setMsg("解密失败");
        }

    }
}
