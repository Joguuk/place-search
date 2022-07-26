package com.jjozerg.search.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName : com.jjozerg.search.common.constants
 * fileName : SearchConstants
 * author : joguk
 * description : Constants
 */
@RequiredArgsConstructor
public class SearchConstants {

    @Getter
    public enum Providers {
        Kakao(1, 5),
        Naver(2, 5);

        private final int sortOrder;
        private final int exposureMaxCount;

        Providers(int sortOrder, int exposureCount) {
            this.sortOrder = sortOrder;
            this.exposureMaxCount = exposureCount;
        }

        /**
         * 노출우선순위가 더 높은지 여부를 반환한다.
         *
         * @author joguk
         * @date 2022/07/26 8:31 오후
         */
        public boolean isPriorityExposure(Providers providers) {
            return this.getSortOrder() < providers.getSortOrder();
        }
    }
}
