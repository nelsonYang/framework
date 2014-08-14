package com.framework.session.local.code;

import com.framework.spi.entity.CodeEntity;
import com.framework.spi.provider.CodeManager;
import com.frameworkLog.factory.LogFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class LocalCodeManager implements CodeManager {

    private final Logger logger = LogFactory.getInstance().getLogger(LocalCodeManager.class);
    private final Map<String, CodeEntity> codeMap = new ConcurrentHashMap<String, CodeEntity>();


    @Override
    public boolean putKey(String key, CodeEntity value) {
        boolean isSuccess = false;
        codeMap.put(key, value);
        isSuccess = true;
        return isSuccess;

    }

    @Override
    public String getKey(String key) {
        String code = "";
        CodeEntity codeEntity = codeMap.get(key);
        if (codeEntity != null) {
            code = codeEntity.getCode();
        }
        return code;
    }

    @Override
    public boolean remove(String key) {
        boolean isSuccess = false;
        codeMap.remove(key);
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public void removeTimeout(long timeout) {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, CodeEntity>> codeIter = codeMap.entrySet().iterator();
        Map.Entry<String, CodeEntity> entry;
        CodeEntity codeEntity;
        while (codeIter.hasNext()) {
            entry = codeIter.next();
            if (entry != null) {
                codeEntity = entry.getValue();
                if (codeEntity != null) {
                    if (currentTime - codeEntity.getCreateTime() >= timeout) {
                        logger.debug("uuid=" + entry.getKey() + ": code=" + codeEntity.getCode());
                        codeIter.remove();
                    }
                }
            }
        }
    }

    @Override
    public boolean putKey(String key, CodeEntity value, int timeout) {
        return this.putKey(key, value);
    }
}
