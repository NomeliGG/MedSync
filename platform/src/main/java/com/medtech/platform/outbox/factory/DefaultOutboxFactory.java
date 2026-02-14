package com.medtech.platform.outbox.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medtech.platform.exception.OperationFailedException;
import com.medtech.platform.outbox.store.OutboxRequest;
import com.medtech.platform.util.ValidationUtils;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Log4j2
@RequiredArgsConstructor
public class DefaultOutboxFactory implements OutboxFactory {

    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Override
    public OutboxRequest createOutboxRequest(String targetTopic, OutboxMessageData data) {
        final Errors errors = new BeanPropertyBindingResult(data, "message");
        validator.validate(data, errors);
        if (errors.hasErrors()) {
            final Map<String, List<String>> errorsPerMessageField = ValidationUtils.errorsToMap(errors);
            log.error("Failed to create outbox due to validation errors:%n{}", errorsPerMessageField);
            throw new OperationFailedException("Outbox message validation failed");
        }

        final Object messagePayload = data.messagePayload();
        final String outboxPayload;
        try {
            outboxPayload = objectMapper.writeValueAsString(messagePayload);
        } catch (JsonProcessingException e) {
            throw new OperationFailedException("Unable to serialize outbox message to JSON", e);
        }
        return new OutboxRequest(
                UUID.randomUUID().toString(),
                data.messageKey(),
                targetTopic,
                outboxPayload,
                messagePayload.getClass()
        );
    }

}
