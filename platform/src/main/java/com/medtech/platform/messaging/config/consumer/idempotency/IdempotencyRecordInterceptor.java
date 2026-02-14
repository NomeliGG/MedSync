package com.medtech.platform.messaging.config.consumer.idempotency;

import com.medtech.platform.messaging.common.DomesticMessageHeader;
import com.medtech.platform.util.time.UtcClock;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.listener.RecordInterceptor;

@Log4j2
@RequiredArgsConstructor
public class IdempotencyRecordInterceptor implements RecordInterceptor<String, Object> {

    private final ProcessedMessageRepository processedMessageRepository;

    @Override
    public ConsumerRecord<String, Object> intercept(
            ConsumerRecord<String, Object> record,
            Consumer<String, Object> consumer
    ) {
        final Header idempotencyHeader = record.headers().lastHeader(DomesticMessageHeader.IDEMPOTENCY_KEY);
        if (idempotencyHeader == null) {
            return record;
        }

        final String idempotencyKey = new String(idempotencyHeader.value());
        if (processedMessageRepository.existsById(idempotencyKey)) {
            log.info("Message [{}] is already processed", idempotencyKey);
            // do not even try store processed message on success
            record.headers().remove(DomesticMessageHeader.IDEMPOTENCY_KEY);
            return null;
        }
        return record;
    }

    @Override
    public void success(ConsumerRecord<String, Object> record, Consumer<String, Object> consumer) {
        final Header idempotencyHeader = record.headers().lastHeader(DomesticMessageHeader.IDEMPOTENCY_KEY);
        if (idempotencyHeader == null) {
            return;
        }
        final ProcessedMessageEntity processedMessage = new ProcessedMessageEntity();
        processedMessage.setIdempotencyKey(new String(idempotencyHeader.value()));
        processedMessage.setCreatedAt(UtcClock.nowLocal());
        processedMessageRepository.save(processedMessage);
    }

}
