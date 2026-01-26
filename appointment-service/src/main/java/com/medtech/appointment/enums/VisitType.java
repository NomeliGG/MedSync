package com.medtech.appointment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum VisitType {
    CONSULTATION("consultation"),
    CHECKUP("checkup"),
    FOLLOWUP("followup");

    private final String code;

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator
    public static VisitType fromCode(String code) {
        for (VisitType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown VisitType code: " + code);
    }
}
