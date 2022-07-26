package com.jjozerg.search.common.config;

import com.jjozerg.search.common.constants.SearchConstants;
import feign.Logger;
import feign.RequestInterceptor;
import feign.Response;
import feign.Target;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;

/**
 * packageName : com.jjozerg.search.common.config
 * fileName : FeignConfig
 * author : joguk
 * description : FeignConfig
 */
@Slf4j
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor(
            @Value("${feign.endpoint.kakao-developers.auth-key}") String kakaoAuthKey,
            @Value("${feign.endpoint.naver-developers.client-id}") String naverClientId,
            @Value("${feign.endpoint.naver-developers.client-secret}") String naverClientSecret,
            @Value("${feign.endpoint.kakao-developers.host}") String kakaoDevelopersHost,
            @Value("${feign.endpoint.naver-developers.host}") String naverDevelopersHost) {
        return requestTemplate -> {
            Target<?> target = requestTemplate.feignTarget();
            if (target.url().equals(kakaoDevelopersHost)) {
                requestTemplate.header("Authorization", kakaoAuthKey);
            } else if (target.url().equals(naverDevelopersHost)) {
                requestTemplate.header("X-Naver-Client-Id", naverClientId);
                requestTemplate.header("X-Naver-Client-Secret", naverClientSecret);
            }
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    public static class CustomErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            log.error("methodKey=>{}",methodKey);
            log.error("response=>{}",response);
            log.error("response.request().headers().toString()=>{}",response.request().headers());
            return new Exception();
        }
    }
}
