package com.framework.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author nelson
 */
@ApplicationException(rollback=true)
public class RollBackException extends RuntimeException {

    private int errorCode = -1;

    public RollBackException(String message) {
        super(message);
    }

    public RollBackException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public RollBackException(String message, Throwable t) {
        super(message, t);
    }

    public RollBackException(String message, int errorCode, Throwable t) {
        super(message, t);
        this.errorCode = errorCode;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
