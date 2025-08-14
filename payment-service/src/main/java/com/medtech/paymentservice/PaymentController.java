package com.medtech.paymentservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external/v1/main")
public class PaymentController {

    @GetMapping
    public String main() {
        return "Hello from payment-service";
    }

}
