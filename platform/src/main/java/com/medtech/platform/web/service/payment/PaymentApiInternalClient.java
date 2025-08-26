package com.medtech.platform.web.service.payment;

import com.medtech.platform.web.service.PlatformFeignClientConfig;
import com.medtech.platform.web.service.Service;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = Service.DiscoveryId.PAYMENT_SERVICE,
        path = PaymentApiInternal.BASE_URL,
        configuration = PlatformFeignClientConfig.class
)
public interface PaymentApiInternalClient extends PaymentApiInternal {

}
