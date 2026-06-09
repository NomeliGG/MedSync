package com.medtech.platform.web.micro.payment.inout;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record PaymentOut(
        @NotNull @Positive Double amount,
        @NotNull @Past LocalDateTime created
) {
}
