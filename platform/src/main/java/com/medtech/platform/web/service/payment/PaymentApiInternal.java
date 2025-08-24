package com.medtech.platform.web.service.payment;

import com.medtech.platform.web.service.payment.inout.PaymentIn;
import com.medtech.platform.web.service.payment.inout.PaymentOut;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentApiInternal {

    String BASE_URL = "/external/v1/payments";

    String PARAM_USER_ID = "userId";
    String PATH_USER_ID = "/{" + PARAM_USER_ID + "}";

    @PostMapping
    void createPayment(@Valid @RequestBody PaymentIn in);

    @GetMapping(PATH_USER_ID)
    List<PaymentOut> getUserPayments(@PathVariable(PARAM_USER_ID) UUID userId);

}
