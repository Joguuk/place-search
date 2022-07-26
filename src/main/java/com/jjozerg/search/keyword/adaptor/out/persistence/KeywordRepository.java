package com.jjozerg.search.keyword.adaptor.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * packageName : com.jjozerg.search.keyword.adaptor.out.persistence
 * fileName : KeywordRepository
 * author : joguk
 * description : KeywordRepository
 */
public interface KeywordRepository extends JpaRepository<KeywordJpaEntity, Long>, KeywordRepositoryCustom {
    Optional<KeywordJpaEntity> findByKeyword(String searchWord);
}
