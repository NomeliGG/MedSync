package com.medtech.userservice.config;

import com.medtech.platform.util.mapper.PlatformObjectMapperBuilderCustomizer;
import com.medtech.platform.util.mapper.SimpleObjectMapper;
import com.medtech.platform.web.PlatformWebMvcConfig;
import com.medtech.platform.web.micro.payment.PaymentApiInternalClient;
import com.medtech.platform.web.security.PlatformSecurityConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@EnableFeignClients(clients = PaymentApiInternalClient.class)
@Import({
        PlatformObjectMapperBuilderCustomizer.class,
        SimpleObjectMapper.class,
        PlatformWebMvcConfig.class,
        PlatformSecurityConfig.class
})
public class UserServiceConfig {

}
