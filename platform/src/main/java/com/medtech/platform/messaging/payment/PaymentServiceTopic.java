package com.medtech.platform.messaging.payment;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PaymentServiceTopic {

    public static final String GROUP_ID = "payment-service-group-1";

    /**
     * Demo topic.
     */
    public static final String NEW_PAYMENT_REQUESTED = "payment.new.payment.requested";

}
