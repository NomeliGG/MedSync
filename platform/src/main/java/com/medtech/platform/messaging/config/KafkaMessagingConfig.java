package com.medtech.platform.messaging.config;

import com.medtech.platform.messaging.common.ServiceAwareKafkaTemplate;
import com.medtech.platform.messaging.config.consumer.idempotency.IdempotencyRecordInterceptor;
import com.medtech.platform.messaging.config.consumer.idempotency.ProcessedMessageRepository;
import java.time.Duration;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Central Kafka messaging configuration.
 *
 * <p>Configures producer, consumer, DLQ handling, and listener container setup
 * for service-based messaging.</p>
 *
 * <p>Provides:
 * <ul>
 *     <li>Common producer with {@link ServiceAwareKafkaTemplate}
 *     to automatically attach sender service headers.</li>
 *
 *     <li>Dedicated DLQ producer for publishing failed messages.</li>
 *
 *     <li>Consumer factory with JSON deserialization and trusted packages configuration.</li>
 *
 *     <li>Idempotency {@link RecordInterceptor} to prevent duplicate message processing.</li>
 *
 *     <li>Listener container factory with retry policy and
 *     {@link DeadLetterPublishingRecoverer} for error handling.</li>
 * </ul>
 */
@Log4j2
@Configuration
@AutoConfigurationPackage
public class KafkaMessagingConfig {

    public static final String PRODUCER_FACTORY_QUALIFIER = "producerFactory";
    public static final String KAFKA_TEMPLATE_QUALIFIER = "kafkaTemplate";

    public static final String DLQ_PRODUCER_FACTORY_QUALIFIER = "dlqProducerFactory";
    public static final String DLQ_KAFKA_TEMPLATE_QUALIFIER = "dlqKafkaTemplate";

    public static final String CONSUMER_FACTORY_QUALIFIER = "consumerFactory";
    public static final String CONTAINER_FACTORY_QUALIFIER = "containerFactory";
    public static final String IDEMPOTENCY_RECORD_INTERCEPTOR_QUALIFIER = "idempotencyRecordInterceptor";

    // Common Producer

    @Bean(PRODUCER_FACTORY_QUALIFIER)
    ProducerFactory<String, String> producerFactory(KafkaProperties properties) {
        Map<String, Object> configs = properties.buildProducerProperties();
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean(KAFKA_TEMPLATE_QUALIFIER)
    KafkaTemplate<String, String> kafkaTemplate(
            @Qualifier(PRODUCER_FACTORY_QUALIFIER) ProducerFactory<String, String> producerFactory,
            ServiceMessagingProperties serviceMessagingProperties
    ) {
        return new ServiceAwareKafkaTemplate<>(producerFactory, serviceMessagingProperties.getCurrentService());
    }

    // DLQ Producer

    @Bean(DLQ_PRODUCER_FACTORY_QUALIFIER)
    ProducerFactory<String, Object> dlqProducerFactory(KafkaProperties properties) {
        Map<String, Object> configs = properties.buildProducerProperties();
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean(DLQ_KAFKA_TEMPLATE_QUALIFIER)
    KafkaTemplate<String, Object> dlqKafkaTemplate(
            @Qualifier(DLQ_PRODUCER_FACTORY_QUALIFIER) ProducerFactory<String, Object> producerFactory,
            ServiceMessagingProperties serviceMessagingProperties
    ) {
        return new ServiceAwareKafkaTemplate<>(producerFactory, serviceMessagingProperties.getCurrentService());
    }

    @Bean(CONSUMER_FACTORY_QUALIFIER)
    ConsumerFactory<String, Object> kafkaConsumerFactory(
            KafkaProperties kafkaProperties,
            ServiceMessagingProperties serviceMessagingProperties
    ) {
        final Map<String, Object> configs = kafkaProperties.buildConsumerProperties();
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, serviceMessagingProperties.getConsumerGroupId());
        configs.put(JsonDeserializer.TRUSTED_PACKAGES, serviceMessagingProperties.getTrustedConsumerPackage());
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean(IDEMPOTENCY_RECORD_INTERCEPTOR_QUALIFIER)
    RecordInterceptor<String, Object> idempotencyRecordInterceptor(ProcessedMessageRepository repository) {
        return new IdempotencyRecordInterceptor(repository);
    }

    @Bean(CONTAINER_FACTORY_QUALIFIER)
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            @Qualifier(CONSUMER_FACTORY_QUALIFIER) ConsumerFactory<String, Object> kafkaConsumerFactory,
            @Qualifier(DLQ_KAFKA_TEMPLATE_QUALIFIER) KafkaTemplate<String, Object> dlqKafkaTemplate,
            @Qualifier(IDEMPOTENCY_RECORD_INTERCEPTOR_QUALIFIER) RecordInterceptor<String, Object> recordInterceptor
    ) {
        final var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        containerFactory.setConsumerFactory(kafkaConsumerFactory);
        containerFactory.setRecordInterceptor(recordInterceptor);
        containerFactory.setConcurrency(1);
        containerFactory.setCommonErrorHandler(new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(dlqKafkaTemplate),
                new FixedBackOff(Duration.ofSeconds(5).toMillis(), 3)
        ));
        return containerFactory;
    }

}
