package com.framework.service.handler;

import com.framework.service.api.TransactionSessionBeanLocal;
import com.framework.service.api.WorkHandler;

/**
 *
 * @author nelson
 */
public class TransactionWorkHandler implements WorkHandler{
    private TransactionSessionBeanLocal transactionSessionBeanLocal;
    private WorkHandler nextWorkHandler;
    public TransactionWorkHandler(TransactionSessionBeanLocal transactionSessionBeanLocal,WorkHandler nextWorkHandler){
        this.transactionSessionBeanLocal = transactionSessionBeanLocal;
        this.nextWorkHandler = nextWorkHandler;
    }
    @Override
    public void execute() {
       transactionSessionBeanLocal.execute(nextWorkHandler);
    }
    
}
