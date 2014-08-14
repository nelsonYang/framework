package com.framework.service.api;

import javax.ejb.Local;

/**
 *
 * @author nelson
 */
@Local
public interface TransactionSessionBeanLocal {
    public void execute(WorkHandler workHandler);
}
