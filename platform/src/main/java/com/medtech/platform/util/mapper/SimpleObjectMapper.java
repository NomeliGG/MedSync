package com.medtech.platform.util.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medtech.platform.exception.OperationFailedException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleObjectMapper {

    private final ObjectMapper objectMapper;

    public <T> T toObject(String json, Class<T> type) {
        return toObject(json.getBytes(StandardCharsets.UTF_8), type);
    }

    public <T> T toObject(byte[] json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new OperationFailedException("Unable to deserialize json to type '%s'".formatted(type), e);
        }
    }

}
