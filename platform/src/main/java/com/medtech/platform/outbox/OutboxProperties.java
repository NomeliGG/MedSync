package com.medtech.platform.outbox;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@EnableConfigurationProperties
@Data
public class OutboxProperties {

    public static final String BASE_PREFIX = "infrastructure.outbox";
    public static final String SCHEDULER_PREFIX = BASE_PREFIX + ".scheduler";

    @Positive
    private int maxProcessingAttempts = 5;

    @Positive
    private int processingBatchSize = 100;

    @Min(2)
    private int processingThreadPoolSize = 3;

    @Positive
    private int processingQueueCapacity = 10_000;

}
