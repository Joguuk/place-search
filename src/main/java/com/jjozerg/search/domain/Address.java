package com.jjozerg.search.domain;

import com.jjozerg.search.place.adaptor.out.dto.KakaoPlaceResDto;
import lombok.Getter;
import lombok.ToString;

/**
 * packageName : com.jjozerg.search.domain
 * fileName : Address
 * author : joguk
 * description : 주소 도메인
 */

@Getter
@ToString
public class Address {
    private final String address;
    private final String roadAddress;

    public Address(String addressName, String roadAddressName) {
        address = addressName;
        roadAddress = roadAddressName;
    }

    public boolean isEqualAddress(Address address) {
        return this.address.equals(address.getAddress())
                && roadAddress.equals(address.getRoadAddress());
    }
}
