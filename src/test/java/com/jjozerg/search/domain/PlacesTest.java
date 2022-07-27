package com.jjozerg.search.domain;

import com.jjozerg.search.common.constants.SearchConstants;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName : com.jjozerg.search.domain
 * fileName : PlacesTest
 * author : joguk
 * description : 장소 일급 콜랙션 테스트
 */
class PlacesTest {

    @Test
    @DisplayName("장소검색 결과 Size 10")
    public void When_Search_Place_Then_Size_10() {
        //given
        List<Place> stub = generatePlacesStub();
        Places sut = new Places(stub);

        //when
        Places places = sut.getSortedPlaceList();

        //then
        assertThat(places.getPlaceList().size())
                .isEqualTo(10);
    }

    @Test
    @DisplayName("중복된 검색 결과가 있는 경우, 상위에 우선 노출(순서보장)")
    public void When_Duplicated_Search_Place_Then_Upper_Exposure() {
        //given
        List<Place> stub = generatePlacesStub();
        Places sut = new Places(stub);

        //when
        Places places = sut.getSortedPlaceList();

        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(places.getPlaceList())
                .extracting("title")
                .containsExactly("GS편의점1","GS편의점2","GS편의점3","GS편의점11","GS편의점12","GS편의점4","GS편의점5","GS편의점6","GS편의점7","GS편의점8","GS편의점10")
                .doesNotContain("GS편의점9","GS편의점10");
        softly.assertThat(places.getPlaceList().size())
                .isEqualTo(12);
    }

    @Test
    @DisplayName("검색 제공자별 최대 5개의 노출 보장(Enum - exposureMaxCount)")
    public void Search_Providers_Exposure_Max_Count() {
        //given
        List<Place> stub = new ArrayList<>();
        generatedKakaoSearchStub(stub, "GS편의점1", "GS편의점2", "GS편의점3", "GS편의점4", "GS편의점5", "GS편의점6", SearchConstants.Providers.Kakao, "GS편의점7", SearchConstants.Providers.Kakao, "GS편의점8", SearchConstants.Providers.Kakao, "GS편의점9", SearchConstants.Providers.Kakao, "GS편의점10", SearchConstants.Providers.Kakao);

        stub.add(new Place("GS편의점11","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        stub.add(new Place("GS편의점12","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        stub.add(new Place("GS편의점13","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        stub.add(new Place("GS편의점14","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        stub.add(new Place("GS편의점15","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        Places sut = new Places(stub);

        //when
        Places places = sut.getSortedPlaceList();

        //then
        List<Place> placeList = places.getPlaceList();

        long naverCount = placeList.stream().filter(it -> it.getProviders().equals(SearchConstants.Providers.Naver)).count();
        long kakaoCount = placeList.stream().filter(it -> it.getProviders().equals(SearchConstants.Providers.Kakao)).count();
        assertThat(naverCount).isEqualTo(SearchConstants.Providers.Naver.getExposureMaxCount());
        assertThat(kakaoCount).isEqualTo(SearchConstants.Providers.Kakao.getExposureMaxCount());
    }

    private List<Place> generatePlacesStub() {
        List<Place> result = new ArrayList<>();
        generatedKakaoSearchStub(result, "GS편의점1", "GS편의점2", "GS편의점3", "GS편의점4", "GS편의점5", "GS편의점6", SearchConstants.Providers.Kakao, "GS편의점7", SearchConstants.Providers.Kakao, "GS편의점8", SearchConstants.Providers.Kakao, "GS편의점9", SearchConstants.Providers.Kakao, "GS편의점10", SearchConstants.Providers.Kakao);

        result.add(new Place("GS편의점1","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        result.add(new Place("GS편의점2","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        result.add(new Place("GS편의점3","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        result.add(new Place("GS편의점7","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));
        result.add(new Place("GS편의점8","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver));

        return result;
    }

    private void generatedKakaoSearchStub(List<Place> stub, String gs편의점1, String gs편의점2, String gs편의점3, String gs편의점4, String gs편의점5, String gs편의점6, SearchConstants.Providers kakao, String gs편의점7, SearchConstants.Providers kakao2, String gs편의점8, SearchConstants.Providers kakao3, String gs편의점9, SearchConstants.Providers kakao4, String gs편의점10, SearchConstants.Providers kakao5) {
        stub.add(new Place(gs편의점1, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), SearchConstants.Providers.Kakao));
        stub.add(new Place(gs편의점2, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), SearchConstants.Providers.Kakao));
        stub.add(new Place(gs편의점3, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), SearchConstants.Providers.Kakao));
        stub.add(new Place(gs편의점4, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), SearchConstants.Providers.Kakao));
        stub.add(new Place(gs편의점5, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), SearchConstants.Providers.Kakao));
        stub.add(new Place(gs편의점6, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), kakao));
        stub.add(new Place(gs편의점7, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), kakao2));
        stub.add(new Place(gs편의점8, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), kakao3));
        stub.add(new Place(gs편의점9, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), kakao4));
        stub.add(new Place(gs편의점10, "00", new Address("서울시 강남구", "서울시 강남구"), new Location("1", "2"), kakao5));
    }
}