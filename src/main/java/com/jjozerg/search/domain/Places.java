package com.jjozerg.search.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.jjozerg.search.common.constants.SearchConstants.Providers;

/**
 * packageName : com.jjozerg.search.domain
 * fileName : placeList
 * author : joguk
 * description : Place 일급 콜랙션
 */
@Getter
public class Places {
    public static final int MAX_PLACES_COUNT = 10;
    private final List<Place> placeList;

    public Places(List<Place> placeList) {
        this.placeList = placeList;
    }

    /**
     * 조건에 맞게 정렬된 장소 목록을 반환한다.
     * 1) 각 검색 제공자별로 중복된 검색 결과는 우선 노출한다.
     *   - html 태그, 띄어쓰기를 제거한 제목과 주소가 완전 일치하는 경우 중복된 장소로 판단
     * 2) 각 검색 제공자별로 노출 가능 개수를 최대한 보장한다(기본값: 각 검색제공자별 5씩 노출 보장)
     * 3) 각 검색 제공자별로 노출 가능 개수를 반환하지 못하는 경우, 검색 제공자를 고려하지 않고 결과에 추가한다.
     *
     * @author joguk
     * @date 2022/07/26 1:48 오후
     */
    public Places getSortedPlaceList() {
        List<Place> sortedList = getDuplicatedPriorityList();

        // 검색 제공사별로 최대한 반환하고자 하는 결과를 추가한다.
        placeList.forEach(it -> addPlaceIfRemainedProvider(sortedList, it));

        // 각 검색 제공자별로 노출 가능 개수를 반환하지 못하는 경우, 검색 제공자를 고려하지 않고 결과에 추가
        placeList.forEach(it -> addPlaceIfOtherPlace(sortedList, it));

        return new Places(sortedList);
    }

    /**
     * 검색 제공자별 노출 가능 개수에 여유가 있다면, 반환 결과에 추가한다.
     *
     * @author joguk
     * @date 2022/07/26 8:09 오후
     */
    private void addPlaceIfRemainedProvider(List<Place> sortedList, Place place) {
        if (isRemainedProvider(sortedList, place.getProviders())) {
            addPlaceIfOtherPlace(sortedList, place);
        }
    }

    /**
     * 다른 장소인 경우, 반환 결과에 추가한다.
     *
     * @author joguk
     * @date 2022/07/26 8:10 오후
     */
    private void addPlaceIfOtherPlace(List<Place> sortedList, Place place) {
        if (sortedList.size() < MAX_PLACES_COUNT && !place.containsEqualPlace(sortedList)) {
            sortedList.add(place);
        }
    }

    /**
     * 검색 제공자별 최대 노출 가능개수를 확인하여 추가 가능한지를 반환한다.
     *
     * @author joguk
     * @date 2022/07/26 5:14 오후
     */
    private boolean isRemainedProvider(List<Place> sortedList, Providers providers) {
        long count = sortedList.stream()
                .filter(it -> it.getProviders().equals(providers))
                .count();

        return sortedList.size() < MAX_PLACES_COUNT && count < providers.getExposureMaxCount();
    }

    /**
     * 중복 검색 리스트를 찾은 후, 우선 노출할 중복 장소 목록을 반환한다.
     *
     * @author joguk
     * @date 2022/07/26 8:10 오후
     */
    private List<Place> getDuplicatedPriorityList() {
        List<Place> notDuplicatedList = new ArrayList<>();
        List<Place> duplicatedList = new ArrayList<>();

        for (Place place : this.placeList) {
            if (!place.isDuplicate(notDuplicatedList)) {
                notDuplicatedList.add(place);
                continue;
            }
            duplicatedList.add(place);
        }

        return duplicatedList;
    }
}
