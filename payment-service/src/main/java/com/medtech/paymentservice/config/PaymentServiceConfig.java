package com.medtech.paymentservice.config;

import com.medtech.platform.messaging.config.KafkaMessagingConfig;
import com.medtech.platform.messaging.config.ServiceMessagingProperties;
import com.medtech.platform.messaging.payment.PaymentServiceTopic;
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
        PlatformWebMvcConfig.class,
        PlatformSecurityConfig.class
})
public class PaymentServiceConfig {

    @Bean
    public ServiceMessagingProperties paymentMessagingProperties() {
        return ServiceMessagingProperties.of(
                Service.PAYMENT_SERVICE,
                PaymentServiceTopic.GROUP_ID,
                PaymentServiceTopic.class
        );
    }

}
