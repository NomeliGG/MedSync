package com.medtech.platform.outbox;

/**
 * Represents the processing state of an Outbox entry.
 */
public enum OutboxStatus {
    /**
     * Message is persisted and awaiting delivery.
     */
    CREATED,
    /**
     * Message was successfully published.
     */
    COMPLETED
}
