package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.service.api.WorkHandler;
import com.framework.spi.entity.SessionEntity;
import com.framework.utils.Base64Utils;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class MobileSessionValidateWorkHandler implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(MobileSessionValidateWorkHandler.class);
    private WorkHandler nextHandler;

    public MobileSessionValidateWorkHandler(WorkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        InvocationContext invocationContext = applicationContext.getInvocationContext();
        Map<String, String> requestMap = invocationContext.getRequestMap();
        //获取session
        String base64Session = requestMap.get("session");
        logger.debug("base64Session=".concat(base64Session));
        if (base64Session != null) {
            //验证session是否过期
            //解析session
            String contents = Base64Utils.decodeToString(base64Session);
            logger.debug("decode contents=".concat(contents));
            if (contents != null) {
                Map<String, String> sessionMap = JsonUtils.parseJsonToMap(contents);
                String userName = sessionMap.get("userName");
                logger.debug("userName=".concat(userName));
                String token = sessionMap.get("token");
                logger.debug("token=".concat(token));
                String userId = sessionMap.get("userId");
                logger.debug("userId=".concat(userId));
                if (userName != null && token != null && userId != null) {
                    SessionEntity session = applicationContext.getSessionManager().getSessionEntityByKey(userId);
                    logger.debug("session=" + session);
                    if (session != null) {
                        session.setTouchTime(System.currentTimeMillis());
                        if (token.equals(session.getToken())) {
                            applicationContext.setUserId(Long.parseLong(userId));
                            applicationContext.setUserName(userName);
                            applicationContext.setTelephoneNum(String.valueOf(userId));
                            nextHandler.execute();
                        } else {
                            applicationContext.setEncryptCode(9000);
                            applicationContext.setMsg("Session无效或者过期");
                        }
                    } else {
                        applicationContext.setEncryptCode(9000);
                        applicationContext.setMsg("Session无效或者过期");
                    }
                } else {
                    applicationContext.setEncryptCode(9000);
                    applicationContext.setMsg("Session无效或者过期");
                }
            } else {
                applicationContext.setEncryptCode(9000);
                applicationContext.setMsg("Session无效或者过期");
            }
        } else {
            applicationContext.setEncryptCode(9000);
            applicationContext.setMsg("Session无效或者过期");
        }
    }
}
