package com.medtech.paymentservice;

import com.medtech.platform.messaging.common.DomesticMessageHeader;
import com.medtech.platform.messaging.config.KafkaMessagingConfig;
import com.medtech.platform.messaging.payment.PaymentServiceTopic;
import com.medtech.platform.messaging.payment.dto.PaymentCreationRequestedDto;
import com.medtech.platform.web.micro.Service;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PaymentExampleListener {

    @KafkaListener(
            topics = PaymentServiceTopic.NEW_PAYMENT_REQUESTED,
            groupId = PaymentServiceTopic.GROUP_ID,
            containerFactory = KafkaMessagingConfig.CONTAINER_FACTORY_QUALIFIER
    )
    public void handleMessage(
            @Valid @Payload PaymentCreationRequestedDto data,
            @Header(DomesticMessageHeader.SENDER_SERVICE) String senderService
    ) {
        log.info("Received data from {}: {}", Service.fromDiscoveryId(senderService), data);
    }

}
