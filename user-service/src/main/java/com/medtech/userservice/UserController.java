package com.medtech.userservice;

import com.medtech.platform.web.service.payment.PaymentApiInternalClient;
import com.medtech.platform.web.service.payment.inout.PaymentIn;
import com.medtech.platform.web.service.payment.inout.PaymentOut;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final PaymentApiInternalClient paymentApi;

    @PostMapping
    public void createPaymentOnUserBehalf(@RequestParam("userId") UUID userId) {
        paymentApi.createPayment(new PaymentIn(userId, 350.99));
    }

    @GetMapping
    public Map<String, Object> getUserPayments(@RequestParam("userId") UUID userId) {
        final List<PaymentOut> payments = paymentApi.getUserPayments(userId);
        return Map.of(
                "userId", userId,
                "payments", payments
        );
    }

}
