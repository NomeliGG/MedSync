package com.medtech.platform.messaging.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record PaymentCreationRequestedDto(
        @NotBlank String appointmentId,
        @Positive double amount,
        @PastOrPresent LocalDate createdAt
) {
}
