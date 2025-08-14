package com.medtech.config.filter;

import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@UtilityClass
public class RequestIdFilterFunctions {

    public static final String REQUEST_ID_HEADER = "X-Request-Id";

    public static HandlerFilterFunction<ServerResponse, ServerResponse> identified() {
        return (ServerRequest request, HandlerFunction<ServerResponse> next) -> {
            String requestId = UUID.randomUUID().toString();
            final ServerRequest updatedRequest = ServerRequest.from(request)
                    .header(REQUEST_ID_HEADER, requestId)
                    .build();
            final ServerResponse serverResponse = next.handle(updatedRequest);
            serverResponse.headers().add(REQUEST_ID_HEADER, requestId);
            return serverResponse;
        };
    }

}
