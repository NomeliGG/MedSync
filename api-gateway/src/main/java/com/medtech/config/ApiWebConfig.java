package com.medtech.config;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class ApiWebConfig implements WebMvcConfigurer {

    private final ApiGatewayCorsProperties corsProperties;

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        corsProperties.getRegistry().forEach((pathPattern, properties) ->
                registry.addMapping(pathPattern)
                        .allowCredentials(properties.allowCredentials())
                        .allowedOriginPatterns(listAsArrayAssured(properties.allowedOriginPatterns()))
                        .allowedOrigins(listAsArrayAssured(properties.allowedOrigins()))
                        .allowedMethods(listAsArrayAssured(properties.allowedMethods()))
                        .allowedHeaders(listAsArrayAssured(properties.allowedHeaders()))
                        .exposedHeaders(listAsArrayAssured(properties.exposedHeaders()))
        );
    }

    @NotNull
    private static String[] listAsArrayAssured(@Nullable List<String> list) {
        return list == null ? new String[0] : list.toArray(new String[0]);
    }

}
