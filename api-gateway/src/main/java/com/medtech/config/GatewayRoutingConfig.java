package com.medtech.config;

import java.util.Map.Entry;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import static com.medtech.config.filter.RequestIdFilterFunctions.identified;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Log4j2
@Configuration
public class GatewayRoutingConfig {

    @Bean
    RouterFunction<ServerResponse> routes(ApiRoutingProperties routingProperties) {
        return routingPropertiesAsFunctions(RouterFunctions.route(), routingProperties).build();
    }

    private static RouterFunctions.Builder routingPropertiesAsFunctions(
            RouterFunctions.Builder builder,
            ApiRoutingProperties routingProperties
    ) {
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
                    .build();

            builder.add(route);
        }
        return builder;
    }

}
