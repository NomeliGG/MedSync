package com.medtech.appointment.controller;

import com.medtech.platform.outbox.factory.OutboxMessageData;
import com.medtech.platform.messaging.payment.PaymentServiceTopic;
import com.medtech.platform.messaging.payment.dto.PaymentCreationRequestedDto;
import com.medtech.platform.outbox.factory.OutboxFactory;
import com.medtech.platform.outbox.store.OutboxRequest;
import com.medtech.platform.outbox.store.OutboxRequestStorage;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/outbox")
@RequiredArgsConstructor
public class OutboxTestController {

    private final OutboxFactory outboxFactory;
    private final OutboxRequestStorage outboxRequestStorage;
    private final Random random = new SecureRandom();

    /**
     * Demo.
     */
    @PostMapping
    @Transactional
    public void test() {
        final var dto = new PaymentCreationRequestedDto(UUID.randomUUID().toString(), random.nextDouble(1, 1000), LocalDate.now());
        final OutboxMessageData messageData = OutboxMessageData.of(dto.appointmentId(), dto);
        final OutboxRequest outboxRequest = outboxFactory.createOutboxRequest(PaymentServiceTopic.NEW_PAYMENT_REQUESTED, messageData);
        outboxRequestStorage.store(outboxRequest);

        log.info("Message sent to {}", PaymentServiceTopic.NEW_PAYMENT_REQUESTED);
    }

}
