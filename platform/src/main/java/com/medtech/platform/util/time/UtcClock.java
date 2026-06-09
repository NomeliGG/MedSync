package com.medtech.platform.util.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UtcClock {

    public static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    public static LocalDateTime nowLocal() {
        return Instant.now().atZone(UTC_ZONE_ID).toLocalDateTime();
    }

    public static LocalDateTime toDateTime(Instant instant) {
        return instant.atZone(UTC_ZONE_ID).toLocalDateTime();
    }

}
