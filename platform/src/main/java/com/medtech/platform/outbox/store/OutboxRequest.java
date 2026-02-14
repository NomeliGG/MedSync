package com.medtech.platform.outbox.store;

import static com.medtech.platform.outbox.OutboxConstraints.IDEMPOTENCY_KEY_LENGTH;
import static com.medtech.platform.outbox.OutboxConstraints.MESSAGE_KEY_LENGTH;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public record OutboxRequest(
        @NotBlank @Size(max = IDEMPOTENCY_KEY_LENGTH) String idempotencyKey,
        @NotBlank @Size(max = MESSAGE_KEY_LENGTH) String messageKey,
        @NotBlank String targetTopic,
        @NotBlank String payload,
        @NotNull Class<?> payloadType
) {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OutboxRequest that = (OutboxRequest) o;
        return Objects.equals(idempotencyKey, that.idempotencyKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idempotencyKey);
    }

}
