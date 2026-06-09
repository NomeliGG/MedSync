package com.medtech.platform.web.security.session;

import com.medtech.platform.util.time.UtcClock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Log4j2
@UtilityClass
public class UserSessionMapper {

    public static Optional<GatewayUserSession> gatewaySessionFrom(
            OAuth2AuthenticationToken oauth2Token,
            Supplier<Optional<UUID>> sessionIdResolveStrategy
    ) {
        final OAuth2User principal = oauth2Token.getPrincipal();
        if (principal instanceof DefaultOidcUser oidcUser) {
            final Optional<UUID> maybeSessionId = sessionIdResolveStrategy.get();
            if (maybeSessionId.isEmpty()) {
                log.error("Missing session ID");
                return Optional.empty();
            }
            return Optional.of(
                    GatewayUserSession.builder()
                            .sessionId(maybeSessionId.get())
                            .userId(UUID.fromString(oidcUser.getSubject()))
                            .userEmail(oidcUser.getEmail())
                            .userFullName(oidcUser.getUserInfo().getFullName())
                            .build()
            );
        }
        return Optional.empty();
    }

    public static Optional<DefaultUserSession> defaultSessionFrom(
            JwtAuthenticationToken authenticationToken,
            Supplier<Optional<UUID>> sessionIdResolveStrategy
    ) {
        final Jwt jwt = authenticationToken.getToken();
        try {
            if (jwt.getExpiresAt() == null) {
                log.error("Missing expiration for JWT");
                return Optional.empty();
            }
            final Optional<UUID> maybeSessionId = sessionIdResolveStrategy.get();
            if (maybeSessionId.isEmpty()) {
                log.error("Missing session ID");
                return Optional.empty();
            }
            LocalDateTime sessionExpiresAt = UtcClock.toDateTime(jwt.getExpiresAt());
            final DefaultUserSession session = DefaultUserSession.builder()
                    .userId(UUID.fromString(jwt.getSubject()))
                    .userFullName(jwt.getClaimAsString("name"))
                    .userEmail(jwt.getClaimAsString("email"))
                    .sessionExpiresAt(sessionExpiresAt)
                    .sessionId(maybeSessionId.get())
                    .build();
            return Optional.of(session);
        } catch (RuntimeException e) {
            log.error("Unable to read session from JWT, cause: {}", e.getMessage());
            return Optional.empty();
        }
    }

}
