package com.medtech.platform.outbox.store;

import com.medtech.platform.outbox.OutboxEntity;
import com.medtech.platform.outbox.OutboxRepository;
import com.medtech.platform.outbox.OutboxStatus;
import com.medtech.platform.util.time.UtcClock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@RequiredArgsConstructor
public class DatabaseOutboxRequestStorage implements OutboxRequestStorage {

    private final OutboxRepository outboxRepository;

    @Override
    @Transactional
    public void store(OutboxRequest... outboxRequests) {
        if (outboxRequests == null || outboxRequests.length == 0) {
            log.warn("No outbox requests were provided to store");
            return;
        }

        final LocalDateTime now = UtcClock.nowLocal();
        final List<OutboxEntity> newOutboxEntities = Arrays.stream(outboxRequests).map(outboxRequest -> {
            final OutboxEntity outboxEntity = new OutboxEntity();
            outboxEntity.setIdempotencyKey(outboxRequest.idempotencyKey());
            outboxEntity.setMessageKey(outboxRequest.messageKey());
            outboxEntity.setTargetTopic(outboxRequest.targetTopic());
            outboxEntity.setPayload(outboxRequest.payload());
            outboxEntity.setPayloadTypeId(outboxRequest.payloadType().getName());
            outboxEntity.setCreatedAt(now);
            outboxEntity.setUpdatedAt(now);
            outboxEntity.setStatus(OutboxStatus.CREATED);
            return outboxEntity;
        }).toList();

        outboxRepository.saveAll(newOutboxEntities);
        log.info("Stored {} outbox request(s)", outboxRequests.length);
    }

}
