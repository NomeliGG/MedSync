package com.medtech.platform.web.micro;

import com.medtech.platform.exception.BadRequestException;
import com.medtech.platform.exception.DataNotFoundException;
import com.medtech.platform.exception.OperationFailedException;
import com.medtech.platform.exception.WrongDataException;
import com.medtech.platform.exception.api.InternalApiErrorOut;
import com.medtech.platform.util.mapper.SimpleObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
public class PlatformErrorDecoder implements ErrorDecoder {

    private final SimpleObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        final InternalApiErrorOut apiError = responseAsApiError(response);
        final HttpStatus status = HttpStatus.valueOf(apiError.getStatus());
        if (status == HttpStatus.BAD_REQUEST) {
            throw new WrongDataException(apiError.getMessage());
        } else if (status == HttpStatus.NOT_FOUND) {
            throw new DataNotFoundException(apiError.getMessage());
        } else if (status.is4xxClientError()) {
            throw new BadRequestException(apiError.getMessage());
        } else {
            throw new OperationFailedException(apiError.getMessage());
        }
    }

    private InternalApiErrorOut responseAsApiError(Response response) {
        final byte[] responseBodyBytes;
        try {
            responseBodyBytes = response.body().asInputStream().readAllBytes();
        } catch (IOException e) {
            throw new OperationFailedException("Unable to read all bytes of response body during api call", e);
        }
        final InternalApiErrorOut apiError = objectMapper.toObject(responseBodyBytes, InternalApiErrorOut.class);
        final Map<String, Collection<String>> headers = response.headers();

        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        headers.forEach((key, values) -> values.forEach(value -> multiValueMap.add(key, value)));

        return apiError.toBuilder()
                .headers(new HttpHeaders(multiValueMap))
                .build();
    }

}
