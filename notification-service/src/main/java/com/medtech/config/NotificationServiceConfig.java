package com.medtech.config;

import com.medtech.platform.util.mapper.PlatformObjectMapperBuilderCustomizer;
import com.medtech.platform.util.mapper.SimpleObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PlatformObjectMapperBuilderCustomizer.class, SimpleObjectMapper.class})
public class NotificationServiceConfig {
}
