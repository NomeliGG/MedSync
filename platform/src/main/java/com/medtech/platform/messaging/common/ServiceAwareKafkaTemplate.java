package com.medtech.platform.messaging.common;

import static com.medtech.platform.messaging.common.DomesticMessageHeader.SENDER_SERVICE;
import static com.medtech.platform.messaging.common.MessagingUtils.headerOf;

import com.medtech.platform.web.micro.Service;
import java.util.concurrent.CompletableFuture;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;

/**
 * Extension of {@link KafkaTemplate} that automatically enriches
 * outgoing Kafka records with a sender service header.
 *
 * <p>Before delegating to the parent {@link #send(ProducerRecord)} method,
 * this template adds a {@link DomesticMessageHeader#SENDER_SERVICE} header containing the
 * discovery identifier of the current service.</p>
 *
 * <p>This ensures that every produced message carries metadata about
 * its origin service, enabling:
 * <ul>
 *     <li>Cross-service tracing</li>
 *     <li>Auditing</li>
 *     <li>Conditional message handling based on sender</li>
 * </ul>
 *
 * <p>The sender service is defined by the {@link Service} enum and
 * injected at construction time.</p>
 *
 * @param <K> Kafka record key type
 * @param <V> Kafka record value type
 */
public class ServiceAwareKafkaTemplate<K, V> extends KafkaTemplate<K, V> {

    private final Service senderService;

    public ServiceAwareKafkaTemplate(ProducerFactory<K, V> producerFactory, Service senderService) {
        super(producerFactory);
        this.senderService = senderService;
    }

    @Override
    public CompletableFuture<SendResult<K, V>> send(ProducerRecord<K, V> producerRecord) {
        final RecordHeader senderServiceHeader = headerOf(SENDER_SERVICE, senderService.getDiscoveryServiceId());
        producerRecord.headers().add(senderServiceHeader);
        return super.send(producerRecord);
    }

}
