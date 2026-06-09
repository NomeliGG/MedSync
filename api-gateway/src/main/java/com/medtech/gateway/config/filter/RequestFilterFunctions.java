package com.medtech.gateway.config.filter;

import com.medtech.platform.web.PlatformHttpHeaders;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Log4j2
@UtilityClass
public class RequestFilterFunctions {

    public static final String REQUEST_ID_HEADER = "X-Request-Id";

    public static HandlerFilterFunction<ServerResponse, ServerResponse> identified() {
        return (ServerRequest request, HandlerFunction<ServerResponse> next) -> {
            String requestId = UUID.randomUUID().toString();
            final ServerRequest updatedRequest = ServerRequest.from(request)
                    .header(REQUEST_ID_HEADER, requestId)
                    .build();
            final ServerResponse serverResponse = next.handle(updatedRequest);
            serverResponse.headers().add(REQUEST_ID_HEADER, requestId);
            if (log.isDebugEnabled()) {
                log.debug("Assigned ID '{}' to request '{}'", requestId, request.path());
            }
            return serverResponse;
        };
    }

    public static HandlerFilterFunction<ServerResponse, ServerResponse> propagateOriginalSessionId() {
        return (ServerRequest request, HandlerFunction<ServerResponse> next) -> {
            final String originalSessionId = request.session().getId();
            return next.handle(ServerRequest.from(request)
                    .header(PlatformHttpHeaders.ORIGINAL_SESSION_ID, originalSessionId)
                    .build());
        };
    }

}
