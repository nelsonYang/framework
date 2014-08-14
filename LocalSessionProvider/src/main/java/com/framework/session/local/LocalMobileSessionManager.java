package com.framework.session.local;

import com.framework.spi.entity.SessionEntity;
import com.framework.spi.provider.SessionManager;
import com.frameworkLog.factory.LogFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class LocalMobileSessionManager implements SessionManager {

    private final Logger logger = LogFactory.getInstance().getLogger(LocalMobileSessionManager.class);
    private final Map<String, SessionEntity> sessionMap = new ConcurrentHashMap<String, SessionEntity>();

    @Override
    public void removeTimeout(long sessionTimeout) {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, SessionEntity>> sessionIter = sessionMap.entrySet().iterator();
        Map.Entry<String, SessionEntity> entry;
        SessionEntity sessionEntity;
        while (sessionIter.hasNext()) {
            entry = sessionIter.next();
            if (entry != null) {
                sessionEntity = entry.getValue();
                if (sessionEntity != null) {
                    if (currentTime - sessionEntity.getTouchTime() >= sessionTimeout) {
                        logger.debug("uuid=" + entry.getKey() + ": session=" + entry.getValue() == null ? "null" : entry.getValue().getToken());
                        sessionIter.remove();
                    }
                }
            }
        }

    }

    @Override
    public boolean putKey(String key, SessionEntity value) {
        boolean isSuccess = false;
        this.sessionMap.put(key, value);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public String getKey(String key) {
        String session = "";
        SessionEntity sessionEntity = sessionMap.get(key);
        if (sessionEntity != null) {
            session = sessionEntity.getSession();
        }
        return session;
    }

    @Override
    public boolean clearKey(String key) {
        boolean isSuccess = false;
        this.sessionMap.clear();
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public SessionEntity getSessionEntityByKey(String key) {
        return sessionMap.get(key);
    }

    @Override
    public boolean putKey(String key, SessionEntity value, int timeoutTime) {
        return this.putKey(key, value);
    }

}
