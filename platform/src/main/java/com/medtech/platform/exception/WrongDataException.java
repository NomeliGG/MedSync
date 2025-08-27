package com.medtech.platform.exception;

public class WrongDataException extends BadRequestException {

    public WrongDataException(String message) {
        super(message);
    }

    public WrongDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
