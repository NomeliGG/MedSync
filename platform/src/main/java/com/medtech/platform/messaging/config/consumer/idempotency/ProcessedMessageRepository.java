package com.medtech.platform.messaging.config.consumer.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessageEntity, String> {

}
