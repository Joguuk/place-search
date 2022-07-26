package com.jjozerg.search.place.adaptor.out.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName : com.jjozerg.search.place.adaptor.out.dto
 * fileName : NaverPlaceResDto
 * author : joguk
 * description : 네이버 장소검색 응답 Dto
 */

@ToString
@Getter
@Builder
public class NaverPlaceResDto {
    @Schema(description="검색 결과 문서의 총 개수를 의미한다.")
    private final int total;
    @Schema(description="검색 결과 문서 중, 문서의 시작점을 의미한다.")
    private final int start;
    @Schema(description="검색된 검색 결과의 개수이다.")
    private final int display;
    @Schema(description="검색 결과 업체, 기관의 분류 정보를 제공한다.")
    private final String category;
    @Schema(description="	XML 포멧에서는 item 태그로, JSON 포멧에서는 items 속성으로 표현된다. 개별 검색 결과이며 title, link, description, address, mapx, mapy를 포함한다.")
    private final List<Item> items;

    @Getter
    public static class Item {
        @Schema(description="검색 결과 업체, 기관명을 나타낸다.")
        private String title;
        @Schema(description="검색 결과 업체, 기관의 상세 정보가 제공되는 네이버 페이지의 하이퍼텍스트 link를 나타낸다.")
        private String link;
        @Schema(description="검색 결과 업체, 기관명에 대한 설명을 제공한다.")
        private String description;
        @Schema(description="빈 문자열 반환. 과거에 제공되던 항목이라 하위 호환성을 위해 존재한다.")
        private String telephone;
        @Schema(description="검색 결과 업체, 기관명의 주소를 제공한다.")
        private String address;
        @Schema(description="검색 결과 업체, 기관명의 도로명 주소를 제공한다.")
        private String roadAddress;
        @Schema(description="검색 결과 업체, 기관명 위치 정보의 x좌표를 제공한다. 제공값은 카텍좌표계 값으로 제공된다. 이 좌표값은 지도 API와 연동 가능하다.")
        private String mapx;
        @Schema(description="검색 결과 업체, 기관명 위치 정보의 y좌표를 제공한다. 제공값은 카텍 좌표계 값으로 제공된다. 이 좌표값은 지도 API와 연동 가능하다.")
        private String mapy;
    }
}
