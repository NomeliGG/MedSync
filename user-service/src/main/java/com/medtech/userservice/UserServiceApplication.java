package com.medtech.userservice;

import com.medtech.platform.util.UtcClock;
import com.medtech.platform.web.service.payment.PaymentApiInternalClient;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableFeignClients(clients = PaymentApiInternalClient.class)
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(UtcClock.UTC_TIME_ZONE);
        Locale.setDefault(Locale.US);
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
