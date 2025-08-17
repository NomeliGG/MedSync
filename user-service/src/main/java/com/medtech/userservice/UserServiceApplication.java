package com.medtech.userservice;

import com.medtech.platform.util.UtcClock;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(UtcClock.UTC_TIME_ZONE);
        Locale.setDefault(Locale.US);
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
