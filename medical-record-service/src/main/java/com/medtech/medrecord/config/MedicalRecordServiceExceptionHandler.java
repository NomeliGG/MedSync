package com.medtech.medrecord.config;

import com.medtech.platform.exception.api.PlatformExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MedicalRecordServiceExceptionHandler extends PlatformExceptionHandler {
}
