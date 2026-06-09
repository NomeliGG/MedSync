package com.medtech.platform.web.micro.payment;

import com.medtech.platform.web.micro.PlatformFeignClientConfig;
import com.medtech.platform.web.micro.Service;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = Service.DiscoveryId.PAYMENT_SERVICE,
        path = PaymentApiInternal.BASE_URL,
        configuration = PlatformFeignClientConfig.class
)
public interface PaymentApiInternalClient extends PaymentApiInternal {

}
