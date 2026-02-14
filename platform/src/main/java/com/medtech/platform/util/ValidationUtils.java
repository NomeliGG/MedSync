package com.medtech.platform.util;

import static java.util.stream.Collectors.toMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@UtilityClass
public class ValidationUtils {

    public static Map<String, List<String>> errorsToMap(Errors errors) {
        return errorsToMap(errors.getAllErrors());
    }

    public static Map<String, List<String>> errorsToMap(List<ObjectError> objectErrors) {
        if (objectErrors == null || objectErrors.isEmpty()) {
            return Map.of();
        }
        return objectErrors.stream()
                .map(objectError -> (FieldError) objectError)
                .filter(fieldError -> Objects.nonNull(fieldError.getDefaultMessage()))
                .collect(toMap(
                        FieldError::getField,
                        fieldError -> {
                            final var errorMessagesList = new LinkedList<String>();
                            errorMessagesList.add(fieldError.getDefaultMessage());
                            return errorMessagesList;
                        },
                        (errorMessagesList, errorMessagesList2) -> {
                            errorMessagesList.addAll(errorMessagesList2);
                            return errorMessagesList;
                        }
                ));
    }

}
