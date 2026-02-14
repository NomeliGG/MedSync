package com.medtech.platform.outbox.factory;

import com.medtech.platform.outbox.store.OutboxRequest;

/**
 * Factory responsible for creating {@link OutboxRequest} instances.
 *
 * <p>Encapsulates the logic of transforming {@link OutboxMessageData}
 * into a persistable Outbox request bound to a specific Kafka topic.</p>
 */
public interface OutboxFactory {

    /**
     * Creates a new Outbox request for the given target topic and message data.
     *
     * @param targetTopic destination Kafka topic
     * @param data message key and payload to be stored in the Outbox
     * @return constructed OutboxRequest ready for persistence
     */
    OutboxRequest createOutboxRequest(String targetTopic, OutboxMessageData data);
}
