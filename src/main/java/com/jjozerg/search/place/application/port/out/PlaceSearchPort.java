package com.jjozerg.search.place.application.port.out;

import com.jjozerg.search.domain.Place;
import com.jjozerg.search.domain.Places;

import java.util.List;

/**
 * packageName : com.jjozerg.search.place.application.port.out
 * fileName : PlaceSearchPort
 * author : joguk
 * description : 장소검색 Port
 */
public interface PlaceSearchPort {
    Places retrievePlaces(String searchWord);
}
