package com.medtech.platform.exception;

public class OperationFailedException extends AbstractLogicException {

    public OperationFailedException(String message) {
        super(message);
    }

    public OperationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
