package com.medtech.paymentservice.config;

import com.medtech.platform.exception.api.PlatformExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PaymentServiceExceptionHandler extends PlatformExceptionHandler {
}
