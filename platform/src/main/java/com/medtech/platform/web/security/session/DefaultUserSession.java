package com.medtech.platform.web.security.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Internal session model propagated from API Gateway to downstream services.
 */
@Getter
@Jacksonized
@Builder
public class DefaultUserSession implements UserSession {
    private final UUID sessionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime sessionExpiresAt;
    private final UUID userId;
    private final String userEmail;
    private final String userFullName;
}
