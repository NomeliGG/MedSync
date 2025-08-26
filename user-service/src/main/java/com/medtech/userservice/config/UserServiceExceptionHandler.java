package com.medtech.userservice.config;

import com.medtech.platform.exception.api.PlatformExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserServiceExceptionHandler extends PlatformExceptionHandler {
}
