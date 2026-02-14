package com.medtech.appointment.config;

import com.medtech.platform.messaging.appointment.AppointmentServiceTopic;
import com.medtech.platform.messaging.config.KafkaMessagingConfig;
import com.medtech.platform.messaging.config.ServiceMessagingProperties;
import com.medtech.platform.outbox.OutboxConfig;
import com.medtech.platform.util.mapper.PlatformObjectMapperBuilderCustomizer;
import com.medtech.platform.util.mapper.SimpleObjectMapper;
import com.medtech.platform.web.service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        PlatformObjectMapperBuilderCustomizer.class,
        SimpleObjectMapper.class,
        KafkaMessagingConfig.class,
        OutboxConfig.class
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
