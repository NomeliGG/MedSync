package com.medtech.platform.outbox.factory;

import static com.medtech.platform.outbox.OutboxConstraints.MESSAGE_KEY_LENGTH;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents the data required to create an Outbox request entry.
 *
 * <h3>Fields</h3>
 *
 * <ul>
 *     <li><b>messageKey</b> — Logical key of the message.
 *     Used as the Kafka record key and determines partitioning and ordering.
 *     Must not be blank and must not exceed {@link com.medtech.platform.outbox.OutboxConstraints#MESSAGE_KEY_LENGTH}.</li>
 *
 *     <li><b>messagePayload</b> — The actual event payload.
 *     Must not be null and is validated recursively if it contains validation annotations.</li>
 * </ul>
 */
public record OutboxMessageData(
        @NotBlank @Size(max = MESSAGE_KEY_LENGTH) String messageKey,
        @NotNull @Valid Object messagePayload
) {

    public static OutboxMessageData of(String targetTopic, Object messagePayload) {
        return new OutboxMessageData(targetTopic, messagePayload);
    }

}
