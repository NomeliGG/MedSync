package com.medtech.platform.outbox.store;

/**
 * Storage abstraction for persisting {@link OutboxRequest} entries.
 *
 * <p>Responsible for saving one or more Outbox requests,
 * typically within the same transactional boundary as business logic.</p>
 */
public interface OutboxRequestStorage {

    /**
     * Persists the given Outbox requests.
     *
     * @param outboxRequests one or more requests to be stored
     */
    void store(OutboxRequest... outboxRequests);
}
