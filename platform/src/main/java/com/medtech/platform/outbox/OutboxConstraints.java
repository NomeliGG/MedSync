package com.medtech.platform.outbox;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OutboxConstraints {
    public static final int IDEMPOTENCY_KEY_LENGTH = 128;
    public static final int MESSAGE_KEY_LENGTH = 128;
}
