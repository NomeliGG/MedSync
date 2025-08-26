package com.medtech.platform.exception.api;

import com.medtech.platform.exception.DataNotFoundException;
import com.medtech.platform.exception.WrongDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PlatformExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorOut> handleDataNotFoundException(ServletWebRequest request, DataNotFoundException ex) {
        return mapToResponseApiError(request, HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(WrongDataException.class)
    public ResponseEntity<ApiErrorOut> handleWrongDataException(ServletWebRequest request, WrongDataException ex) {
        return mapToResponseApiError(request, HttpStatus.BAD_REQUEST, ex);
    }

    protected static ResponseEntity<ApiErrorOut> mapToResponseApiError(
            ServletWebRequest request,
            HttpStatus status,
            Exception ex
    ) {
        final ApiErrorOut error = ApiErrorOut.builder()
                .path(request.getRequest().getRequestURI())
                .status(status.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(status).body(error);
    }

}
