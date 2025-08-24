package com.medtech.platform.web.service.payment;

import com.medtech.platform.web.service.Service;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = Service.DiscoveryId.PAYMENT_SERVICE,
        path = PaymentApiInternal.BASE_URL
)
public interface PaymentApiInternalClient extends PaymentApiInternal {

}
