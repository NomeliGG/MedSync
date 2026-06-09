package com.medtech.platform.web.micro;

import com.medtech.platform.util.mapper.SimpleObjectMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlatformFeignClientConfig {

    @Bean
    ErrorDecoder errorDecoder(SimpleObjectMapper objectMapper) {
        return new PlatformErrorDecoder(objectMapper);
    }

}
