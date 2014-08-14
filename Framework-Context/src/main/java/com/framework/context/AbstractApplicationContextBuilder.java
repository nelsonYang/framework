package com.framework.context;

import com.framework.cache.spi.cache.RollBackCache;
import com.framework.crypto.CryptoManager;
import com.framework.entity.builder.DAOContextBuilder;
import com.framework.entity.builder.EntityContextBuilder;
import com.framework.entity.builder.ExtendedEntityContextBuilder;
import com.framework.entity.configuration.ProviderConfigurationManager;
import com.framework.entity.context.DAOContext;
import com.framework.field.FieldManager;
import com.framework.fieldtype.FieldTypeManager;
import com.framework.httpclient.HttpConnectionFactory;
import com.framework.parser.ServiceConfigParser;
import com.framework.parser.TimerParser;
import com.framework.register.ServiceRegisterManager;
import com.framework.response.ResponseWriterManager;
import com.framework.entity.threadlocal.RollBackCacheThreadLocalManager;
import com.framework.threadpool.ThreadPoolManager;
import com.framework.utils.filter.FilterManager;
import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public abstract class AbstractApplicationContextBuilder {

    private final Logger logger = LogFactory.getInstance().getLogger(AbstractApplicationContextBuilder.class);

    protected abstract void buildConfiguration();

    protected abstract void buildSessionManager();

    public final void baseBuild() {
        //
        logger.info("开始构建ApplicationContext ......");
        logger.info("开始构建threadLocal");
        RollBackCacheThreadLocalManager<InvocationContext> threadLocalManager = new RollBackCacheThreadLocalManager<InvocationContext>();
        ApplicationContext.CTX.setThreadLocalManager(threadLocalManager);
        logger.info("完成构建threadLocal");
        //build threadLocal
        logger.info("开始构建threadPool");
        ApplicationContext.CTX.setThreadPoolManager(ThreadPoolManager.getInstance());
        logger.info("完成构建threadPool");
        //build configuration
        logger.info("开始构建configuration");
        this.buildConfiguration();
        logger.info("完成构建configuration");
        //build filter
        logger.info("开始构建 filter");
        ApplicationContext.CTX.setFilterManager(FilterManager.getInstance());
        logger.info("完成构建 filter");
        //build session manager
        logger.info("开始构建session manager");
        this.buildSessionManager();
        logger.info("完成构建session manager");
        //build service
        logger.info("开始构建service");
        boolean isLocal = ApplicationContext.CTX.getLocalSession();
        logger.debug("isLocal={}", isLocal);
        String packagesStr = ApplicationContext.CTX.getPackages();
        logger.debug("packagesStr={}", packagesStr);
        String[] packages;
        if (packagesStr.contains(".")) {
            packages = packagesStr.split(",");
        } else {
            packages = new String[]{packagesStr};
        }
        ServiceConfigParser serviceConfigParser = new ServiceConfigParser();
        serviceConfigParser.parse(packages);
        ApplicationContext.CTX.setServiceRegisterManager(ServiceRegisterManager.getInstance());
        logger.info("完成构建service");
        //build timer Task
        logger.info("开始构建timer Task");
        TimerParser timerParser = new TimerParser();
        timerParser.parse(packages);
        logger.info("完成构建timer Task");
        //build crypt
        logger.info("开始构建 crypt");
        ApplicationContext.CTX.setCryptoManager(CryptoManager.getInstance());
        logger.info("完成构建 crypt");
        //build fileType
        logger.info("开始构建 fileType");
        ApplicationContext.CTX.setFieldTypeManager(FieldTypeManager.getInstance());
        logger.info("完成构建fileType");
        //build responseWriter
        logger.info("开始构建responseWriter");
        ApplicationContext.CTX.setResponseWriterManager(ResponseWriterManager.getInstance());
        logger.info("完成构建responseWriter");
        //build httpClient
        logger.info("开始构建httpClient");
        ApplicationContext.CTX.setHttpConnectionFactory(HttpConnectionFactory.getInstance());
        logger.info("完成构建httpClient");
        //build fieldManager
        logger.info("开始构建fieldManager");
        ApplicationContext.CTX.setFieldManager(FieldManager.getInstance());
        logger.info("完成构建fieldManager");
        //build DAO
        logger.info("开始解析dao");
        DAOContextBuilder daoContextBuilder = new DAOContextBuilder();
        daoContextBuilder.setEntityContextBuilder(new EntityContextBuilder()).setExtendedEntityContextBuilder(new ExtendedEntityContextBuilder()).setProviderConfigurationManager(ProviderConfigurationManager.getInstance()).setPackages(packages).setThreadLocalManager(new RollBackCacheThreadLocalManager<RollBackCache>());
        DAOContext daoContext = daoContextBuilder.build();
        ApplicationContext.CTX.setDaoContext(daoContext);
        logger.info("完成解析dao");
        logger.info("完成构建ApplicationContext ......");

    }
}
