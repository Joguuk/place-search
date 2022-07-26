package com.jjozerg.search.keyword.adaptor.out.persistence;

import java.util.List;

/**
 * packageName : com.jjozerg.search.keyword.adaptor.out.persistence
 * fileName : KeywordRepositoryCustom
 * author : joguk
 * description : KeywordRepositoryCustom
 */
public interface KeywordRepositoryCustom {
    List<KeywordJpaEntity> selectPopularTenKeyword();

    void increaseSearchCount(KeywordJpaEntity keywordJpaEntity);
}
