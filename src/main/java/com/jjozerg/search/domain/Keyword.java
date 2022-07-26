package com.jjozerg.search.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * packageName : com.jjozerg.search.domain
 * fileName : Keyword
 * author : joguk
 * description : 검색 키워드 도메인
 */

@Getter
@ToString
@Builder
public class Keyword {
    private final Long id;
    private final String keyword;
    private final Long keywordSearchCount;
}
