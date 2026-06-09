package com.medtech.gateway.config;

import static com.medtech.gateway.config.filter.RequestFilterFunctions.identified;
import static com.medtech.gateway.config.filter.RequestFilterFunctions.propagateOriginalSessionId;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions.tokenRelay;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

import com.medtech.gateway.bff.BackendForFrontendRoutes;
import com.medtech.platform.web.micro.Service;
import java.util.Map.Entry;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Log4j2
@Configuration
public class GatewayRoutingConfig {

    @Bean
    RouterFunction<ServerResponse> routes(ApiRoutingProperties routingProperties) {
        return apiRouter(routingProperties).and(gatewayRouter());
    }

    private static RouterFunction<ServerResponse> apiRouter(ApiRoutingProperties routingProperties) {
        final RouterFunctions.Builder builder = RouterFunctions.route();
        for (Entry<Service, String> routeEntry : routingProperties.getRoutes().entrySet()) {
            final Service service = routeEntry.getKey();
            final String servicePrefix = routeEntry.getValue();
            if (!service.isPropagatedToApi()) {
                log.warn("Ignoring not propagated api route: service={}, prefix={}", service, servicePrefix);
                continue;
            }
            final RouterFunction<ServerResponse> route = RouterFunctions.route()
                    .route(path(servicePrefix + "/**"), http())
                    .before(rewritePath(servicePrefix + "/?(?<segment>.*)", "/external/$\\{segment}"))
                    .filter(identified())
                    .filter(lb(service.getDiscoveryServiceId()))
                    .filter(tokenRelay().andThen(propagateOriginalSessionId()))
                    .build();

            builder.add(route);
        }
        return builder.build();
    }

    private static RouterFunction<ServerResponse> gatewayRouter() {
        final RouterFunctions.Builder builder = RouterFunctions.route();
        for (BackendForFrontendRoutes r : BackendForFrontendRoutes.values()) {
            builder.route(r.getPredicate(), r.getHandlerFunction());
        }
        return builder.build();
    }

}
