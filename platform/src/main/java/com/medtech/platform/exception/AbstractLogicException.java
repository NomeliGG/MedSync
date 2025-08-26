package com.medtech.platform.exception;

public class AbstractLogicException extends RuntimeException {

    public AbstractLogicException() {
        super();
    }

    public AbstractLogicException(Throwable cause) {
        super(cause);
    }

    public AbstractLogicException(String message) {
        super(message);
    }

    public AbstractLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AbstractLogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
