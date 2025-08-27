package com.medtech.appointment.config;

import com.medtech.platform.exception.api.PlatformExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppointmentServiceExceptionHandler extends PlatformExceptionHandler {
}
