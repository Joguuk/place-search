package com.jjozerg.search.place.adaptor.out;

import com.jjozerg.search.common.constants.SearchConstants;
import com.jjozerg.search.domain.Address;
import com.jjozerg.search.domain.Location;
import com.jjozerg.search.domain.Place;
import com.jjozerg.search.domain.Places;
import com.jjozerg.search.place.adaptor.in.web.dto.PlaceSearchResDto;
import com.jjozerg.search.place.adaptor.out.dto.KakaoPlaceResDto;
import com.jjozerg.search.place.adaptor.out.dto.NaverPlaceResDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : com.jjozerg.search.place.adaptor.out
 * fileName : PlaceMapper
 * author : joguk
 * description : Place 객체를 변환하는 Mapper 클래스
 */

@Component
public class PlaceMapper {
    /**
     * 카카오 검색결과를 Place 도메인으로 변환한다.
     *
     * @author joguk
     * @date 2022/07/26 8:27 오후
     */
    public Place mapToDomainEntity(KakaoPlaceResDto.Document document) {
        return new Place(
                document.getPlace_name(),
                document.getPhone(),
                new Address(document.getAddress_name(), document.getRoad_address_name()),
                new Location(document.getX(), document.getY()),
                SearchConstants.Providers.Kakao);
    }

    /**
     * 네이버 검색결과를 Place 도메인으로 변환한다.
     *
     * @author joguk
     * @date 2022/07/26 8:28 오후
     */
    public Place mapToDomainEntity(NaverPlaceResDto.Item item) {
        return new Place(
                item.getTitle(),
                item.getTelephone(),
                new Address(item.getAddress(), item.getRoadAddress()),
                new Location(item.getMapx(), item.getMapy()),
                SearchConstants.Providers.Naver);
    }

    /**
     * Place 도메인을 응답 Dto로 변환한다.
     *
     * @author joguk
     * @date 2022/07/26 8:28 오후
     */
    public List<PlaceSearchResDto> mapToResponseDto(Places places) {
        List<Place> placeList = places.getPlaceList();
        return placeList.stream()
                .map(PlaceSearchResDto::new)
                .collect(Collectors.toList());
    }
}
