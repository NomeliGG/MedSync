package com.medtech.gateway.bff;

import com.medtech.platform.exception.api.ApiErrorOut;
import com.medtech.platform.util.time.UtcClock;
import com.medtech.platform.web.security.session.UserSessionMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@UtilityClass
public final class BackendForFrontendFunctions {

    public static ServerResponse me(ServerRequest request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauth2Token) {
            return UserSessionMapper.gatewaySessionFrom(
                            oauth2Token,
                            () -> Optional.of(request.session().getId()).map(UUID::fromString)
                    )
                    .map(session -> ServerResponse.ok().body(session))
                    .orElseGet(() -> ofApiError(ApiErrorOut.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .timestamp(UtcClock.nowLocal())
                            .path(request.path())
                            .message("Unable to get user session")
                            .build()));
        } else {
            return ofApiError(ApiErrorOut.builder()
                    .status(HttpStatus.FORBIDDEN.value())
                    .timestamp(UtcClock.nowLocal())
                    .path(request.path())
                    .build());
        }
    }

    private static ServerResponse ofApiError(ApiErrorOut error) {
        return ServerResponse.status(error.getStatus()).body(error);
    }

}
