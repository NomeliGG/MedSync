package com.medtech.platform.util.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class PlatformObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder.modules(new JavaTimeModule());
        builder.postConfigurer(objectMapper -> objectMapper
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        );
    }

}
