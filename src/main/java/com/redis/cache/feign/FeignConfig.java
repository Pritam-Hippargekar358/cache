package com.redis.cache.feign;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableFeignClients
@EnableRetry
public class FeignConfig {

    @Bean
    public Retryer retryer() {
        return new FeignRetryer();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

//    @Bean
//    public Retryer retryer() {
//        return new Retryer.Default(1000, 2000, 4);
//    }
//
//    @Bean
//    public Request.Options options() {
//        return new Request.Options(5, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true);
//    }
}
