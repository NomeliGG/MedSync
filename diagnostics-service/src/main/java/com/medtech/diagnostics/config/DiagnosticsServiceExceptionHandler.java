package com.medtech.diagnostics.config;

import com.medtech.platform.exception.api.PlatformExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DiagnosticsServiceExceptionHandler extends PlatformExceptionHandler {
}
