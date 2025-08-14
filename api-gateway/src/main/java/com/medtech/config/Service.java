package com.medtech.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Service {
    USER_SERVICE("user-service", true),
    PAYMENT_SERVICE("payment-service", true),
    NOTIFICATION_SERVICE("notification-service", false),
    MEDICAL_RECORD_SERVICE("medical-record-service", true),
    DIAGNOSTICS_SERVICE("diagnostics-service", false),
    APPOINTMENT_SERVICE("appointment-service", true),
    ;

    private final String discoveryServiceId;
    private final boolean isPropagatedToApi;

}
