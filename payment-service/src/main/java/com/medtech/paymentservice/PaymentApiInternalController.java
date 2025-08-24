package com.medtech.paymentservice;

import static com.medtech.platform.web.service.payment.PaymentApiInternal.BASE_URL;

import com.medtech.platform.util.UtcClock;
import com.medtech.platform.web.service.payment.PaymentApiInternal;
import com.medtech.platform.web.service.payment.inout.PaymentIn;
import com.medtech.platform.web.service.payment.inout.PaymentOut;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_URL)
public class PaymentApiInternalController implements PaymentApiInternal {

    private final Map<UUID, List<PaymentOut>> userPayments = new HashMap<>();

    @Override
    public void createPayment(PaymentIn in) {
        userPayments.computeIfAbsent(in.userId(), userId -> new ArrayList<>())
                .add(new PaymentOut(in.amount(), UtcClock.nowLocal()));
    }

    @Override
    public List<PaymentOut> getUserPayments(UUID userId) {
        return userPayments.getOrDefault(userId, List.of());
    }

}
