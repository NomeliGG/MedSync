package com.medtech.config;

import com.medtech.platform.exception.api.PlatformExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationServiceExceptionHandler extends PlatformExceptionHandler {
}
