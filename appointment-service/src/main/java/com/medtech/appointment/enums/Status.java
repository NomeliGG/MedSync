package com.medtech.appointment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    BOOKED("booked"),
    CANCELLED_BY_PATIENT("cancelled_by_patient"),
    CANCELLED_BY_DOCTOR("cancelled_by_doctor"),
    COMPLETED("completed");

    private final String code;

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static Status fromCode(String code) {
        for (Status status : values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown Status code: " + code);
    }
}
