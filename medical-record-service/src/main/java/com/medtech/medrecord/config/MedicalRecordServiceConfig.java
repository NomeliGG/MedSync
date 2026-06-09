package com.medtech.medrecord.config;

import com.medtech.platform.util.mapper.PlatformObjectMapperBuilderCustomizer;
import com.medtech.platform.util.mapper.SimpleObjectMapper;
import com.medtech.platform.web.PlatformWebMvcConfig;
import com.medtech.platform.web.security.PlatformSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        PlatformObjectMapperBuilderCustomizer.class,
        SimpleObjectMapper.class,
        PlatformWebMvcConfig.class,
        PlatformSecurityConfig.class
})
public class MedicalRecordServiceConfig {
}
