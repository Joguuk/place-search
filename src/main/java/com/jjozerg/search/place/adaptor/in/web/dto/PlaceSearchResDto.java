package com.jjozerg.search.place.adaptor.in.web.dto;

import com.jjozerg.search.domain.Place;
import lombok.Getter;

/**
 * packageName : com.jjozerg.search.place.adaptor.in.web.dto
 * fileName : PlaceSearchResDto
 * author : joguk
 * description : 장소검색 응답 DTO
 */

@Getter
public class PlaceSearchResDto {
    private final String title;
    private final String contact;
    private final String address;
    private final String roadAddress;
    private final String mapX;
    private final String mapY;

    public PlaceSearchResDto(Place place) {
        this.title = place.getTitle();
        this.contact = place.getContact();
        this.address = place.getAddress().getAddress();
        this.roadAddress = place.getAddress().getRoadAddress();
        this.mapX = place.getLocation().getMapX();
        this.mapY = place.getLocation().getMapY();
    }
}
