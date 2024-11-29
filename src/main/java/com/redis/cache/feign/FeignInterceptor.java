package com.redis.cache.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignInterceptor implements RequestInterceptor {
    private static final String TOKEN_REQUEST_PARAM = "token";
    @Value("${app.user.token}")
    private String token;

    @Override
    public void apply(RequestTemplate template) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        template.header(HttpHeaders.AUTHORIZATION, httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        template.header("Authorization", "Bearer " + getAuthToken());
        template.query(TOKEN_REQUEST_PARAM, token);
    }

    private String getAuthToken() {
        return "your-auth-token";
    }
}
