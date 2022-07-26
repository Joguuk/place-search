package com.jjozerg.search.domain;

import lombok.Getter;
import lombok.ToString;

/**
 * packageName : com.jjozerg.search.domain
 * fileName : Location
 * author : joguk
 * description : 위치 도메인
 */

@Getter
@ToString
public class Location {
    private final String mapX;
    private final String mapY;

    public Location(String x, String y) {
        mapX = x;
        mapY = y;
    }
}
