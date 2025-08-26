package com.medtech.userservice.config;

import com.medtech.platform.util.mapper.PlatformObjectMapperBuilderCustomizer;
import com.medtech.platform.util.mapper.SimpleObjectMapper;
import com.medtech.platform.web.service.payment.PaymentApiInternalClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@EnableFeignClients(clients = PaymentApiInternalClient.class)
@Import({PlatformObjectMapperBuilderCustomizer.class, SimpleObjectMapper.class})
public class UserServiceConfig {

}
