package com.jjozerg.search.place.application.port.in;

import com.jjozerg.search.domain.Places;

/**
 * packageName : com.jjozerg.search.place.application.port.in
 * fileName : PlaceSearchUseCase
 * author : joguk
 * description : 장소 검색 UseCase
 */

public interface PlaceSearchUseCase {
    Places searchPlaceList(String searchWord);
}
