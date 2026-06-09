package com.medtech.platform.web.security.session;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Session model returned by the API Gateway to the client (e.g. /me endpoint).<br/><br/>
 * Represents a lightweight, user-facing view of the session containing identity and profile information needed by the frontend.
 */
@Getter
@Jacksonized
@Builder
public class GatewayUserSession implements UserSession {
    private final UUID sessionId;
    private final UUID userId;
    private final String userEmail;
    private final String userFullName;
}
