package com.medtech.platform.exception.api;

import com.medtech.platform.util.time.UtcClock;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder(toBuilder = true)
@Jacksonized
public class ApiErrorOut {

    private final int status;

    @NotNull
    private final String message;

    @NotNull
    private final String path;

    @Nullable
    private final Object details;

    @NotNull
    @Builder.Default
    private final LocalDateTime timestamp = UtcClock.nowLocal();

}
