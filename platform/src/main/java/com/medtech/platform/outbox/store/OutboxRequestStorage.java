package com.medtech.platform.outbox.store;

import java.util.Set;

/**
 * Storage abstraction for persisting {@link OutboxRequest} entries.
 *
 * <p>Responsible for saving one or more Outbox requests,
 * typically within the same transactional boundary as business logic.</p>
 */
public interface OutboxRequestStorage {

    /**
     * Persists the given Outbox request(s).
     *
     * @param outboxRequests one or more requests to be stored
     */
    default void store(OutboxRequest... outboxRequests) {
        Set<OutboxRequest> outboxSet = outboxRequests == null || outboxRequests.length == 0
                ? Set.of() : Set.of(outboxRequests);
        store(outboxSet);
    }

    /**
     * Persists the given Outbox requests.
     *
     * @param outboxRequests set of outbox requests to be stored
     */
    void store(Set<OutboxRequest> outboxRequests);
}
