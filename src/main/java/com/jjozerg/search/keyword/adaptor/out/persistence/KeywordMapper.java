package com.jjozerg.search.keyword.adaptor.out.persistence;

import com.jjozerg.search.domain.Keyword;
import com.jjozerg.search.keyword.adaptor.out.persistence.dto.KeywordPopularResDto;
import org.springframework.stereotype.Component;

/**
 * packageName : com.jjozerg.search.keyword.adaptor.out.persistence
 * fileName : KeywordMapper
 * author : joguk
 * description : keyword 객체를 변환하는 Mapper 클래스
 */

@Component
public class KeywordMapper {
    public Keyword mapToDomainEntity(KeywordJpaEntity keywordJpaEntity) {
        return Keyword.builder()
                .id(keywordJpaEntity.getId())
                .keyword(keywordJpaEntity.getKeyword())
                .keywordSearchCount(keywordJpaEntity.getKeywordSearchCount())
                .build();
    }

    public KeywordPopularResDto mapToResponseDto(Keyword keyword) {
        return KeywordPopularResDto.builder()
                .keyword(keyword.getKeyword())
                .keywordSearchCount(keyword.getKeywordSearchCount())
                .build();
    }
}
