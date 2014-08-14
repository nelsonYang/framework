package com.framework.service.handler;

import com.framework.service.api.TransactionSessionBeanLocal;
import com.framework.service.api.WorkHandler;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author nelson
 */
@Stateless
@Startup
@EJB(name = "framework/TransactionSessionBean", beanInterface = TransactionSessionBeanLocal.class)
public class TransactionSessionBean implements TransactionSessionBeanLocal {

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void execute(WorkHandler workHandler) {
        workHandler.execute();
    }
}
