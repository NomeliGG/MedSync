package com.medtech.platform.messaging.config.consumer.idempotency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "processed_message")
@Getter
@Setter
public class ProcessedMessageEntity {

    @Id
    @Column(length = 128)
    private String idempotencyKey;

    @NotNull
    private LocalDateTime createdAt;

}
