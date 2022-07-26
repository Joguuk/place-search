package com.jjozerg.search.keyword.adaptor.out.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jjozerg.search.keyword.adaptor.out.persistence.QKeywordJpaEntity.keywordJpaEntity;

/**
 * packageName : com.jjozerg.search.keyword.adaptor.out.persistence
 * fileName : KeywordRepositoryImpl
 * author : joguk
 * description : KeywordRepositoryImpl
 */
public class KeywordRepositoryImpl implements KeywordRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public KeywordRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<KeywordJpaEntity> selectPopularTenKeyword() {
        return queryFactory
                .selectFrom(keywordJpaEntity)
                .orderBy(keywordJpaEntity.keywordSearchCount.desc())
                .limit(10)
                .fetch();
    }

    @Override
    public void increaseSearchCount(KeywordJpaEntity keyword) {
        queryFactory.update(keywordJpaEntity)
                .set(keywordJpaEntity.keywordSearchCount, keywordJpaEntity.keywordSearchCount.add(1))
                .where(keywordJpaEntity.id.eq(keyword.getId()))
                .execute();
    }

}
