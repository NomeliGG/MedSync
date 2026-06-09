package com.medtech.gateway.bff;

import static org.springframework.web.servlet.function.RequestPredicates.GET;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.ServerResponse;

@Getter
@RequiredArgsConstructor
public enum BackendForFrontendRoutes {
    ME(GET("/bff/me"), BackendForFrontendFunctions::me);

    private final RequestPredicate predicate;
    private final HandlerFunction<ServerResponse> handlerFunction;

}
