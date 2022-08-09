package com.jjozerg.search.domain;

import com.jjozerg.search.common.constants.SearchConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName : com.jjozerg.search.domain
 * fileName : PlaceTest
 * author : joguk
 * description : Place 도메인 테스트
 */
class PlaceTest {

    @Test
    @DisplayName("장소 이름과 주소가 동일한 경우 같은 장소로 판단")
    public void When_Place_Plane_Name_And_Address_Equals_Then_True() {
        //given
        Place sut = new Place("GS편의점","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Kakao);
        Place stub = new Place("GS편의점","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver);

        //when
        boolean duplicate = sut.isDuplicate(Collections.singletonList(stub));

        //then
        assertThat(duplicate).isTrue();
    }

    @Test
    @DisplayName("장소 이름과 주소가 동일한 경우 같은 장소로 판단(이름에 태그와 띄어쓰기가 포함)")
    public void When_Place_Tag_White_Space_Name_And_Address_Equals_Then_True() {
        //given
        Place sut = new Place("GS편의점","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Kakao);
        Place stub = new Place("<html> GS편의점</html>","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver);

        //when
        boolean duplicate = sut.isDuplicate(Collections.singletonList(stub));

        //then
        assertThat(duplicate).isTrue();
    }

    @Test
    @DisplayName("장소 이름이 같아도 주소가 다른 경우 다른 주소로 판단")
    public void When_Place_Name_Equal_But_Address_Diff_Then_False() {
        //given
        Place sut = new Place("GS편의점","00", new Address("서울시 강남구1","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Kakao);
        Place stub = new Place("GS편의점","00", new Address("서울시 강남구","서울시 강남구"), new Location("1","2"), SearchConstants.Providers.Naver);

        //when
        boolean duplicate = sut.isDuplicate(Collections.singletonList(stub));

        //then
        assertThat(duplicate).isFalse();
    }
}