package com.medtech.appointment.config;

import com.medtech.platform.messaging.appointment.AppointmentServiceTopic;
import com.medtech.platform.messaging.config.KafkaMessagingConfig;
import com.medtech.platform.messaging.config.ServiceMessagingProperties;
import com.medtech.platform.outbox.OutboxConfig;
import com.medtech.platform.util.mapper.PlatformObjectMapperBuilderCustomizer;
import com.medtech.platform.util.mapper.SimpleObjectMapper;
import com.medtech.platform.web.PlatformWebMvcConfig;
import com.medtech.platform.web.micro.Service;
import com.medtech.platform.web.security.PlatformSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        PlatformObjectMapperBuilderCustomizer.class,
        SimpleObjectMapper.class,
        KafkaMessagingConfig.class,
        OutboxConfig.class,
        PlatformWebMvcConfig.class,
        PlatformSecurityConfig.class
})
public class AppointmentServiceConfig {

    @Bean
    ServiceMessagingProperties appointmentMessagingProperties() {
        return ServiceMessagingProperties.of(
                Service.APPOINTMENT_SERVICE,
                AppointmentServiceTopic.GROUP_ID,
                AppointmentServiceTopic.class
        );
    }

}
