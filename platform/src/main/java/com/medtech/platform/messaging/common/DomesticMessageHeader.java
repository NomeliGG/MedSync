package com.medtech.platform.messaging.common;

import com.medtech.platform.web.service.Service;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DomesticMessageHeader {

    /**
     * This header must contain a {@link Service#getDiscoveryServiceId()} value of a service that created and sent message to a topic.
     */
    public static final String SENDER_SERVICE = "sender-service";

    public static final String TYPE_ID = "__TypeId__";

    public static final String IDEMPOTENCY_KEY = "idempotency-key";

}
