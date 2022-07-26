package com.jjozerg.search.place.adaptor.out;

import com.jjozerg.search.common.annotation.WebAdaptor;
import com.jjozerg.search.domain.Place;
import com.jjozerg.search.domain.Places;
import com.jjozerg.search.place.adaptor.out.dto.KakaoPlaceResDto;
import com.jjozerg.search.place.adaptor.out.dto.NaverPlaceResDto;
import com.jjozerg.search.place.application.port.out.PlaceSearchPort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : com.jjozerg.search.place.adaptor.out
 * fileName : PlaceSearchAdaptor
 * author : joguk
 * description : 장소검색 Adaptor
 */

@RequiredArgsConstructor
@WebAdaptor
public class PlaceSearchAdaptor implements PlaceSearchPort {
    private final PlaceMapper placeMapper;
    private final KakaoFeignClient kakaoFeignClient;
    private final NaverFeignClient naverFeignClient;

    /**
     * 장소 검색 제공자로부터 검색어를 통해 장소를 검색한다.
     *
     * @author joguk
     * @date 2022/07/26 8:27 오후
     */
    @Override
    public Places retrievePlaces(String searchWord) {
        List<Place> places = new ArrayList<>();
        places.addAll(retrieveKakaoPlace(searchWord));
        places.addAll(retrieveNaverPlace(searchWord));

        return new Places(places);
    }

    /**
     * 카카오를 통해 장소를 검색한다.
     *
     * @author joguk
     * @date 2022/07/26 8:27 오후
     */
    private List<Place> retrieveKakaoPlace(String searchWord) {
        KakaoPlaceResDto kakaoPlaceResDto = kakaoFeignClient.selectPlaceByKeyword(searchWord, KakaoFeignClient.DEFAULT_DISPLAY_COUNT);
        List<KakaoPlaceResDto.Document> documents = kakaoPlaceResDto.getDocuments();

        return documents.stream()
                .map(placeMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    /**
     * 네이버를 통해 장소를 검색한다.
     *
     * @author joguk
     * @date 2022/07/26 8:27 오후
     */
    private List<Place> retrieveNaverPlace(String searchWord) {
        NaverPlaceResDto naverPlaceResDto = naverFeignClient.selectPlaceByKeyword(searchWord, NaverFeignClient.DEFAULT_DISPLAY_COUNT);
        List<NaverPlaceResDto.Item> items = naverPlaceResDto.getItems();

        return items.stream()
                .map(placeMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
