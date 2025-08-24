package com.medtech.platform.web.service.payment.inout;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record PaymentIn(
        @NotNull UUID userId,
        @NotNull @Positive Double amount
) {
}
