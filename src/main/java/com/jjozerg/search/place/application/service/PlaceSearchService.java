package com.jjozerg.search.place.application.service;

import com.jjozerg.search.common.annotation.UseCase;
import com.jjozerg.search.domain.Places;
import com.jjozerg.search.keyword.application.service.KeywordService;
import com.jjozerg.search.place.application.port.in.PlaceSearchUseCase;
import com.jjozerg.search.place.application.port.out.PlaceSearchPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * packageName : com.jjozerg.search.place.application.service
 * fileName : PlaceSearchService
 * author : joguk
 * description : 장소검색 Service
 */

@Slf4j
@UseCase
@RequiredArgsConstructor
public class PlaceSearchService implements PlaceSearchUseCase {
    private final PlaceSearchPort placeSearchPort;
    private final KeywordService keywordService;

    /**
     * 검색어에 해당하는 장소 목록을 조회한 후 검색 키워드를 저장한다.
     *
     * @author joguk
     * @date 2022/07/26 8:17 오후
     */
    @Override
    public Places searchPlaceList(String searchWord) {
        Places places = placeSearchPort.retrievePlaces(searchWord);
        keywordService.saveKeyword(searchWord);

        return places.getSortedPlaceList();
    }
}
