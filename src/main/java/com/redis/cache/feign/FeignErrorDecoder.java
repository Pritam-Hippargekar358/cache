package com.redis.cache.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static feign.FeignException.errorStatus;
//https://whackd.in/custom-feign-client-builder-in-spring-boot-gotchas/
//https://github.com/kartik1502/Spring-Boot-Microservices-Banking-Application/blob/main/Transaction%20Service/src/main/java/org/training/transactions/service/implementation/TransactionServiceImpl.java
//https://levelup.gitconnected.com/feign-client-retry-and-timeout-configurations-214eaf14a4d1
@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {
//    private final ErrorDecoder errorDecoder = new Default();
    private final ObjectMapper objectMapper;
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.status() > 499) {
                FeignException exception = errorStatus(methodKey, response);
                return new RetryableException(response.status(), exception.getMessage(), response.request().httpMethod(),
                        exception, (Long) null,
                        response.request());
            }//Date.from(Instant.now().plus(15, ChronoUnit.MILLIS))

            String responseAsString = new String(response.body()
                    .asInputStream().readAllBytes(), "UTF-8");
            ClientExceptionDetails clientException = objectMapper
                    .readValue(responseAsString, ClientExceptionDetails.class);
            clientException.setMethod(methodKey);
            // return defaultErrorDecoder.decode(methodKey, response);
            throw new ClientException(clientException);
        } catch (IOException ex) {
            throw new RuntimeException("Error occurred while decoding error response.", ex);
        }
    }
}
