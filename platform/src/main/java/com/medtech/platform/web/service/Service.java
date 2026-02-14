package com.medtech.platform.web.service;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@RequiredArgsConstructor
@Getter
public enum Service {
    USER_SERVICE(DiscoveryId.USER_SERVICE, true),
    PAYMENT_SERVICE(DiscoveryId.PAYMENT_SERVICE, true),
    NOTIFICATION_SERVICE(DiscoveryId.NOTIFICATION_SERVICE, false),
    MEDICAL_RECORD_SERVICE(DiscoveryId.MEDICAL_RECORD_SERVICE, true),
    DIAGNOSTICS_SERVICE(DiscoveryId.DIAGNOSTICS_SERVICE, false),
    APPOINTMENT_SERVICE(DiscoveryId.APPOINTMENT_SERVICE, true),
    ;

    private final String discoveryServiceId;
    private final boolean isPropagatedToApi;

    @UtilityClass
    public static class DiscoveryId {

        public static final String USER_SERVICE = "user-service";
        public static final String PAYMENT_SERVICE = "payment-service";
        public static final String NOTIFICATION_SERVICE = "notification-service";
        public static final String MEDICAL_RECORD_SERVICE = "medical-record-service";
        public static final String DIAGNOSTICS_SERVICE = "diagnostics-service";
        public static final String APPOINTMENT_SERVICE = "appointment-service";

    }

    @Nullable
    public static Service fromDiscoveryId(@Nullable String discoveryId) {
        if (discoveryId == null || discoveryId.isBlank()) {
            return null;
        }
        for (var v : values()) {
            if (v.getDiscoveryServiceId().equals(discoveryId)) {
                return v;
            }
        }
        return null;
    }

}
