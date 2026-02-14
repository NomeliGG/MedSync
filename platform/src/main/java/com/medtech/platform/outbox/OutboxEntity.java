package com.medtech.platform.outbox;

import com.medtech.platform.persistence.AuditableEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "outbox")
@Getter
@Setter
public class OutboxEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = OutboxConstraints.IDEMPOTENCY_KEY_LENGTH, unique = true, nullable = false)
    private String idempotencyKey;

    @NotBlank
    @Column(length = OutboxConstraints.MESSAGE_KEY_LENGTH)
    private String messageKey;

    @NotBlank
    private String payload;

    @NotBlank
    @Column(length = 256)
    private String payloadTypeId;

    @NotBlank
    @Column(length = 128)
    private String targetTopic;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    @PositiveOrZero
    private int attempts;

    @Nullable
    private String error;

}
