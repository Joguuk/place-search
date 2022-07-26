package com.jjozerg.search.keyword.adaptor.out.persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * packageName : com.jjozerg.search.keyword.adaptor.out.persistence.dto
 * fileName : KeywordPopularResDto
 * author : joguk
 * description : 인기 키워드 응답 Dto
 */

@Getter
@ToString
@Builder
public class KeywordPopularResDto {
    private final String keyword;
    private final Long keywordSearchCount;
}
