package com.medtech.platform.messaging.common;

import static java.nio.charset.StandardCharsets.UTF_8;

import lombok.experimental.UtilityClass;
import org.apache.kafka.common.header.internals.RecordHeader;

@UtilityClass
public class MessagingUtils {

    public static RecordHeader headerOf(String name, String value) {
        return new RecordHeader(name, value.getBytes(UTF_8));
    }

}
