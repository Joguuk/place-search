package com.jjozerg.search.place.adaptor.out.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * packageName : com.jjozerg.search.place.adaptor.out.dto
 * fileName : KakaoPlaceResDto
 * author : joguk
 * description : 카카오 장소검색 응답 Dto
 */

@ToString
@Getter
@Builder
public class KakaoPlaceResDto {
    private final List<Document> documents;
    private final Meta meta;

    @Getter
    public static class Document {
        @Schema(description="장소 ID")
        private String id;
        @Schema(description="장소명, 업체명")
        private String place_name;
        @Schema(description="카테고리 이름")
        private String category_name;
        @Schema(description="중요 카테고리만 그룹핑한 카테고리 그룹 코드")
        private String category_group_code;
        @Schema(description="중요 카테고리만 그룹핑한 카테고리 그룹명")
        private String category_group_name;
        @Schema(description="전화번호")
        private String phone;
        @Schema(description="전체 지번 주소")
        private String address_name;
        @Schema(description="전체 도로명 주소")
        private String road_address_name;
        @Schema(description="X 좌표값, 경위도인 경우 longitude (경도)")
        private String x;
        @Schema(description="Y 좌표값, 경위도인 경우 latitude(위도)")
        private String y;
        @Schema(description="장소 상세페이지 URL")
        private String place_url;
        @Schema(description="중심좌표까지의 거리 (단, x,y 파라미터를 준 경우에만 존재). 단위 meter")
        private String distance;
    }

    public static class Meta {
        @Schema(description="검색어에 검색된 문서 수")
        private Integer total_count;
        @Schema(description="total_count 중 노출 가능 문서 수 (최대: 45)")
        private Integer pageable_count;
        @Schema(description="현재 페이지가 마지막 페이지인지 여부. 값이 false면 다음 요청 시 page 값을 증가시켜 다음 페이지 요청 가능")
        private Boolean is_end;
        @Schema(description="질의어의 지역 및 키워드 분석 정보")
        private RegionInfo same_name;

        public static class RegionInfo {
            @Schema(description="질의어에서 인식된 지역의 리스트. 예: '중앙로 맛집' 에서 중앙로에 해당하는 지역 리스트")
            private String[] region;
            @Schema(description="질의어에서 지역 정보를 제외한 키워드. 예: '중앙로 맛집' 에서 '맛집'")
            private String keyword;
            @Schema(description="인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보")
            private String selected_region;
        }
    }
}
