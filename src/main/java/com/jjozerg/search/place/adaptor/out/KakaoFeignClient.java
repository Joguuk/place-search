package com.jjozerg.search.place.adaptor.out;

import com.jjozerg.search.common.config.FeignConfig;
import com.jjozerg.search.place.adaptor.out.dto.KakaoPlaceResDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

/**
 * packageName : com.jjozerg.search.place.adaptor.out
 * fileName : KakaoFeignClient
 * author : joguk
 * description : KakaoFeignClient
 */
@FeignClient(
        name = "${feign.endpoint.kakao-developers.name}",
        url = "${feign.endpoint.kakao-developers.host}",
        configuration = FeignConfig.class,
        fallbackFactory = KakaoFeignClientFallbackFactory.class)
public interface KakaoFeignClient {
    int DEFAULT_DISPLAY_COUNT = 10;

    @GetMapping("/v2/local/search/keyword")
    KakaoPlaceResDto selectPlaceByKeyword(
            @RequestParam(name = "query") String searchWord,
            @RequestParam(name = "size") int size);
}

@Component
class KakaoFeignClientFallbackFactory implements FallbackFactory<KakaoFeignClient> {
    @Override
    public KakaoFeignClient create(Throwable cause) {
        return new KakaoFeignClient() {
            @Override
            public KakaoPlaceResDto selectPlaceByKeyword(String searchWord, int i) {
                return KakaoPlaceResDto.builder()
                        .documents(Collections.emptyList())
                        .build();
            }
        };
    }
}