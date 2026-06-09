package com.medtech.userservice;

import com.medtech.platform.web.micro.payment.PaymentApiInternalClient;
import com.medtech.platform.web.micro.payment.inout.PaymentIn;
import com.medtech.platform.web.micro.payment.inout.PaymentOut;
import com.medtech.platform.web.security.session.DefaultUserSession;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final PaymentApiInternalClient paymentApi;

    @PostMapping
    public void createPaymentOnUserBehalf(DefaultUserSession session) {
        paymentApi.createPayment(new PaymentIn(session.getUserId(), 350.99));
    }

    @GetMapping
    public Map<String, Object> getUserPayments(DefaultUserSession session) {
        final List<PaymentOut> payments = paymentApi.getUserPayments(session.getUserId());
        return Map.of(
                "userId", session.getUserId(),
                "payments", payments
        );
    }

}
