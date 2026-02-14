package com.medtech.platform.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medtech.platform.outbox.factory.DefaultOutboxFactory;
import com.medtech.platform.outbox.factory.OutboxFactory;
import com.medtech.platform.outbox.store.DatabaseOutboxRequestStorage;
import com.medtech.platform.outbox.store.OutboxRequestStorage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.Validator;

@Configuration
@EnableScheduling
@AutoConfigurationPackage
public class OutboxConfig {

    public static final String OUTBOX_TASK_EXECUTOR_QUALIFIER = "outboxTaskExecutor";

    @Bean
    @ConfigurationProperties(OutboxProperties.BASE_PREFIX)
    OutboxProperties outboxProperties() {
        return new OutboxProperties();
    }

    @Bean(OUTBOX_TASK_EXECUTOR_QUALIFIER)
    TaskExecutor taskExecutor(OutboxProperties outboxProperties) {
        final var taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(outboxProperties.getProcessingThreadPoolSize());
        taskExecutor.setQueueCapacity(outboxProperties.getProcessingQueueCapacity());
        return taskExecutor;
    }

    @Bean
    OutboxRelay outboxRelay(
            OutboxProperties outboxProperties,
            TransactionTemplate transactionTemplate,
            KafkaTemplate<String, String> kafkaTemplate,
            @Qualifier(OUTBOX_TASK_EXECUTOR_QUALIFIER) TaskExecutor taskExecutor,
            OutboxRepository outboxRepository
    ) {
        return new OutboxRelay(
                outboxProperties,
                taskExecutor,
                transactionTemplate,
                kafkaTemplate,
                outboxRepository
        );
    }

    @Bean
    OutboxFactory outboxFactory(ObjectMapper objectMapper, Validator validator) {
        return new DefaultOutboxFactory(objectMapper, validator);
    }

    @Bean
    OutboxRequestStorage outboxRequestStorage(OutboxRepository outboxRepository) {
        return new DatabaseOutboxRequestStorage(outboxRepository);
    }

}
