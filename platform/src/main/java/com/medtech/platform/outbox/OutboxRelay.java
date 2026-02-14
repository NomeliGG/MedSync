package com.medtech.platform.outbox;

import static com.medtech.platform.messaging.common.MessagingUtils.headerOf;
import static com.medtech.platform.outbox.OutboxConfig.OUTBOX_TASK_EXECUTOR_QUALIFIER;
import static com.medtech.platform.outbox.OutboxProperties.SCHEDULER_PREFIX;

import com.medtech.platform.messaging.common.DomesticMessageHeader;
import com.medtech.platform.persistence.projection.LongId;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Limit;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Scheduled component responsible for relaying persisted Outbox messages to Kafka.
 *
 * <p>Periodically polls the Outbox table for pending entries,
 * processes them asynchronously, and publishes records to Kafka.
 * Each Outbox entry is handled in a separate transaction.</p>
 *
 * <p>On successful publishing, the entry is marked as COMPLETED.
 * On failure, the error is stored and the attempt counter is incremented.</p>
 *
 * <p>Processing respects configured batch size and maximum retry attempts.</p>
 */
@Log4j2
@RequiredArgsConstructor
public class OutboxRelay {

    private final OutboxProperties outboxProperties;

    @Qualifier(OUTBOX_TASK_EXECUTOR_QUALIFIER)
    private final TaskExecutor taskExecutor;
    private final TransactionTemplate transactionTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OutboxRepository outboxRepository;

    @PostConstruct
    void init() {
        log.info("OutboxRelay initialized");
    }

    @Async(OUTBOX_TASK_EXECUTOR_QUALIFIER)
    @Scheduled(
            initialDelayString = "${" + SCHEDULER_PREFIX + ".initialDelayInSeconds:5}",
            fixedDelayString = "${" + SCHEDULER_PREFIX + ".fixedDelayInSeconds:30}",
            timeUnit = TimeUnit.SECONDS
    )
    void scheduleOutboxProcessing() {
        final List<Long> outboxIds = outboxRepository.findIdByStatusAndAttemptsLessThanEqualOrderByCreatedAt(
                OutboxStatus.CREATED,
                outboxProperties.getMaxProcessingAttempts(),
                Limit.of(outboxProperties.getProcessingBatchSize())
        ).stream().map(LongId::getId).toList();

        if (outboxIds.isEmpty()) {
            return;
        }

        final CompletableFuture<?>[] futures = outboxIds.stream()
                .map(outboxId -> CompletableFuture.runAsync(
                        () -> transactionTemplate.executeWithoutResult(ts -> doProcessOutbox(outboxId)),
                        taskExecutor
                ))
                .toArray(CompletableFuture<?>[]::new);
        try {
            CompletableFuture.allOf(futures).join();
        } catch (Exception e) {
            log.error("Error occurred while waiting outboxes completion %s".formatted(outboxIds), e);
        }
    }

    private void doProcessOutbox(long outboxId) {
        log.info("Picked outbox '{}' for processing", outboxId);
        final Optional<OutboxEntity> maybeOutbox = outboxRepository.findById(outboxId);
        if (maybeOutbox.isEmpty()) {
            log.info("Outbox '{}' not found", outboxId);
            return;
        }

        final OutboxEntity outbox = maybeOutbox.get();
        try {
            final RecordHeader typeIdHeader = headerOf(DomesticMessageHeader.TYPE_ID, outbox.getPayloadTypeId());
            final RecordHeader idempotencyHeader = headerOf(DomesticMessageHeader.IDEMPOTENCY_KEY, outbox.getIdempotencyKey());
            final ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    outbox.getTargetTopic(),
                    null,
                    System.currentTimeMillis(),
                    outbox.getMessageKey(),
                    outbox.getPayload(),
                    List.of(typeIdHeader, idempotencyHeader)
            );

            kafkaTemplate.send(producerRecord).join();
            outbox.setStatus(OutboxStatus.COMPLETED);
            outbox.setError(null);
        } catch (Exception e) {
            log.error("Failed to process outbox '%d'".formatted(outbox.getId()), e);
            outbox.setError(e.getMessage());
        }
        outbox.setAttempts(outbox.getAttempts() + 1);
        outboxRepository.save(outbox);
    }

}
