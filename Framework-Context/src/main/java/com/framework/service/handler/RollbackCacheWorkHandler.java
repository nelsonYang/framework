package com.framework.service.handler;

import com.framework.cache.spi.cache.RollBackCache;
import com.framework.context.ApplicationContext;
import com.framework.entity.context.DAOContext;
import com.framework.entity.threadlocal.RollBackCacheThreadLocalManager;
import com.framework.exception.RollBackException;
import com.framework.service.api.WorkHandler;
import com.frameworkLog.factory.LogFactory;
import org.slf4j.Logger;

/**
 *
 * @author Administrator
 */
public class RollbackCacheWorkHandler implements WorkHandler {

    private final Logger logger = LogFactory.getInstance().getLogger(RollbackCacheWorkHandler.class);
    private WorkHandler nextHandler;

    public RollbackCacheWorkHandler(WorkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        DAOContext daoContext = applicationContext.getDaoContext();
        RollBackCacheThreadLocalManager<RollBackCache> rollbackThreadLocalManager = daoContext.getThreadLocalManager();
        try {
            RollBackCache rollBackCache = daoContext.buildRollBackCache();
            rollbackThreadLocalManager.openThreadLocal(rollBackCache);
            nextHandler.execute();
        } catch (Exception ex) {
            logger.error("rollback exception", ex);
            RollBackCache rollBackCache = rollbackThreadLocalManager.getValue();
            rollBackCache.rollback();
            if (ex instanceof RollBackException) {
                int errorCode = ((RollBackException) ex).getErrorCode();
                if (errorCode != -1) {
                    applicationContext.fail(errorCode);
                }
               throw new RollBackException("rollback Cache",errorCode, ex);
            } else {
                throw new RollBackException("rollback Cache", ex);
            }
        } finally {
            rollbackThreadLocalManager.closeThreadLocal();
        }
    }
}
