package com.medtech.platform.web.security.session;

import java.util.UUID;

/**
 * Base abstraction for user session data used across services.
 * Defines the minimal identity contract required to identify a user in the system.
 */
public interface UserSession {
    UUID getSessionId();

    UUID getUserId();
}
