package com.medtech.platform.exception.api;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpHeaders;

@Getter
@SuperBuilder(toBuilder = true)
@Jacksonized
public class InternalApiErrorOut extends ApiErrorOut {

    @NotNull
    private final HttpHeaders headers;

}
