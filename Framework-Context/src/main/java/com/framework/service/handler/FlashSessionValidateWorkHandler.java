package com.framework.service.handler;

import com.framework.context.ApplicationContext;
import com.framework.context.InvocationContext;
import com.framework.service.api.WorkHandler;
import com.framework.spi.entity.SessionEntity;
import com.framework.utils.Base64Utils;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class FlashSessionValidateWorkHandler implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(FlashSessionValidateWorkHandler.class);
    private WorkHandler nextHandler;

    public FlashSessionValidateWorkHandler(WorkHandler nextHandler) {
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
                String orgId = sessionMap.get("orgId");
                logger.debug("orgId=".concat(orgId));
                String orgType = sessionMap.get("orgType");
                logger.debug("orgType=".concat(orgType));
                String orgUserId = sessionMap.get("orgUserId");
                logger.debug("orgUserId=".concat(orgUserId));
                String regionId = sessionMap.get("regionId");
                logger.debug("regionId=".concat(regionId));
                String childOrgIds = sessionMap.get("childOrgIds");
                logger.debug("childOrgIds=".concat(childOrgIds));
                if (userName != null && token != null && userId != null) {
                    HttpSession httpSession = applicationContext.getHttpSession();
                    String flashSessionId = httpSession.getAttribute("flashSessionId").toString();
                    logger.debug("flashSessionId=" + flashSessionId);
                    String sessionKey = flashSessionId.trim() + "-" + userId;
                    logger.debug("sessionKey=" + sessionKey);
                    SessionEntity session = applicationContext.getWebSessionManager().getSessionEntityByKey(sessionKey.trim());
                    logger.debug("session=" + session);
                    if (session != null) {
                        session.setTouchTime(System.currentTimeMillis());
                        if (token.equals(session.getToken())) {
                            applicationContext.setUserName(userName);
                            applicationContext.setUserId(Long.parseLong(userId));
                            applicationContext.setOrgId(Integer.parseInt(orgId));
                            applicationContext.setOrgType(Integer.parseInt(orgType));
                            applicationContext.setOrgUserId(Long.parseLong(orgUserId));
                            applicationContext.setRegionId(Integer.parseInt(regionId));
                            applicationContext.setChildOrgIds(childOrgIds);
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
