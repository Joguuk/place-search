package com.jjozerg.search.place.adaptor.out;

import com.jjozerg.search.common.config.FeignConfig;
import com.jjozerg.search.place.adaptor.out.dto.KakaoPlaceResDto;
import com.jjozerg.search.place.adaptor.out.dto.NaverPlaceResDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

/**
 * packageName : com.jjozerg.search.place.adaptor.out
 * fileName : NaverFeignClient
 * author : joguk
 * description : NaverFeignClient
 */
@FeignClient(
        name = "${feign.endpoint.naver-developers.name}",
        url = "${feign.endpoint.naver-developers.host}",
        configuration = FeignConfig.class,
        fallbackFactory = NaverFeignClientFallbackFactory.class)
public interface NaverFeignClient {
    int DEFAULT_DISPLAY_COUNT = 5;

    @GetMapping("/v1/search/local.json")
    NaverPlaceResDto selectPlaceByKeyword(
            @RequestParam(name="query") String searchWord,
            @RequestParam(name="display") int displayCount);
}

class NaverFeignClientFallbackFactory implements FallbackFactory<NaverFeignClient> {
    @Override
    public NaverFeignClient create(Throwable cause) {
        return new NaverFeignClient() {
            @Override
            public NaverPlaceResDto selectPlaceByKeyword(String searchWord, int displayCount) {
                return NaverPlaceResDto.builder()
                        .items(Collections.emptyList())
                        .build();
            }
        };
    }
}