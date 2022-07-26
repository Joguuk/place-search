package com.jjozerg.search.domain;

import com.jjozerg.search.common.utils.StringUtils;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

import static com.jjozerg.search.common.constants.SearchConstants.*;

/**
 * packageName : com.jjozerg.search.domain
 * fileName : Place
 * author : joguk
 * description : 장소 도메인
 */

@Getter
@ToString
public class Place {
    private final String title;
    private final String contact;
    private final Address address;
    private final Location location;
    private final Providers providers;

    public Place(String placeName, String phone, Address address, Location location, Providers providers) {
        this.title = placeName;
        this.contact = phone;
        this.address = address;
        this.location = location;
        this.providers = providers;
    }

    /**
     * 인자로 전달받은 장소 목록이 중복되는지 확인하여 반환한다.
     *
     * @author joguk
     * @date 2022/07/26 1:28 오후
     */
    public boolean isDuplicate(List<Place> placeList) {
        for (Place place : placeList) {
            if (this.providers.isPriorityExposure(place.providers) && isEqualPlace(place)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 같은 장소인지 반환한다.
     * HTML 태그와 띄어쓰기를 제외한 제목이 동일하고 주소가 동일한 경우 같은 장소로 판단한다.
     *
     * @author joguk
     * @date 2022/07/26 1:28 오후
     */
    public boolean isEqualPlace(Place place) {
        String replaceTitle = StringUtils.removeTagAndWhiteSpace(title);

        return replaceTitle.equals(StringUtils.removeTagAndWhiteSpace(place.getTitle()))
                && address.isEqualAddress(place.getAddress());
    }

    /**
     * 장소목록을 전달 받아 동일한 장소를 포한하는지 여부를 반환한다.
     *
     * @author joguk
     * @date 2022/07/26 8:28 오후
     */
    public boolean containsEqualPlace(List<Place> placeList) {
        for (Place place : placeList) {
            if (isEqualPlace(place)) {
                return true;
            }
        }
        return false;
    }
}
