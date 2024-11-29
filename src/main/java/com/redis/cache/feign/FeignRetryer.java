package com.redis.cache.feign;

import feign.RetryableException;
import feign.Retryer;

//feign.client.config.default.retryer.maxAttempts=3
//feign.client.config.default.retryer.backoff.period=200
//feign.client.config.default.retryer.backoff.maxPeriod=2000
public class FeignRetryer implements Retryer {

    private final int maxAttempts;
    private final long backoff;
    private int attempt = 1;

    public FeignRetryer() {
        this(2000, 3);
    }

    public FeignRetryer(long backoff, int maxAttempts) {
        this.backoff = backoff;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void continueOrPropagate(RetryableException ex) {
        if (attempt++ >= maxAttempts) {
            throw ex;
        }
        try {
            Thread.sleep(backoff);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Retryer clone() {
        return new FeignRetryer(backoff, maxAttempts);
    }
}
