package com.framework.register;

import com.framework.service.api.WorkHandler;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class ServiceRegisterManager {

    
    private static  ServiceRegisterManager serviceRegisterManager;
    private static final Map<String, WorkHandler> serviceMap = new HashMap<String, WorkHandler>(64, 1);
    
    private ServiceRegisterManager() {
    }
    public static synchronized ServiceRegisterManager getInstance() {
        if (serviceRegisterManager == null) {
            serviceRegisterManager = new ServiceRegisterManager();
        }
        return serviceRegisterManager;
    }
    
    public void addService(String serviceName,WorkHandler service){
        serviceMap.put(serviceName, service);
    }
    
    public WorkHandler getService(String serviceName){
        return serviceMap.get(serviceName);
    }
}
