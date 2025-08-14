package com.medtech.config;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Component
@Validated
@ConfigurationProperties("cors")
@EnableConfigurationProperties
@Data
public class ApiGatewayCorsProperties {

    @NotNull
    private Map<@NotBlank String, @Valid CorsProperties> registry = Map.of();

    public record CorsProperties(
            @Nullable List<@NotBlank String> allowedOrigins,
            @Nullable List<@NotBlank String> allowedOriginPatterns,
            @NotNull List<@NotBlank String> allowedMethods,
            @NotNull List<@NotBlank String> allowedHeaders,
            @NotNull List<@NotBlank String> exposedHeaders,
            boolean allowCredentials
    ) {
    }

}
