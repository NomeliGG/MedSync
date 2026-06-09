package com.medtech.diagnostics;

import com.medtech.platform.util.time.UtcClock;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        HibernateJpaAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class
})
public class DiagnosticsServiceApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(UtcClock.UTC_TIME_ZONE);
        Locale.setDefault(Locale.US);
        SpringApplication.run(DiagnosticsServiceApplication.class, args);
    }

}
