package com.medtech.gateway;

import com.medtech.platform.util.time.UtcClock;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {
        HibernateJpaAutoConfiguration.class,
        DataSourceAutoConfiguration.class
})
public class ApiGatewayApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(UtcClock.UTC_TIME_ZONE);
        Locale.setDefault(Locale.US);
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
