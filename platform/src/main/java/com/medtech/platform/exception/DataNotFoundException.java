package com.medtech.platform.exception;

public class DataNotFoundException extends BadRequestException {

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
