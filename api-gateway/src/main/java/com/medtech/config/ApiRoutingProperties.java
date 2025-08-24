package com.medtech.config;

import com.medtech.platform.web.service.Service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties("routing")
@EnableConfigurationProperties
@Data
public class ApiRoutingProperties {

    @Valid
    private Map<@NotNull Service, @NotBlank String> routes;

}
