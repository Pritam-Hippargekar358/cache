package com.redis.cache.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientExceptionDetails {
    private int status;
    private String error;
    private String path;
    private String method;
    private String timestamp;
}
