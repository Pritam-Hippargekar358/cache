package com.redis.cache.feign;

public class ClientException extends RuntimeException {

    public ClientException(ClientExceptionDetails exceptionDetails) {
        super(String.format("The method %s got exception with status: %s and message: %s calling path %s at %s ",
                exceptionDetails.getMethod(), exceptionDetails.getStatus(), exceptionDetails.getError(), exceptionDetails.getPath(),
                exceptionDetails.getTimestamp()));
    }
}
