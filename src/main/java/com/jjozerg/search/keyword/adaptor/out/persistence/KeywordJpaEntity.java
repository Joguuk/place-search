package com.jjozerg.search.keyword.adaptor.out.persistence;

import com.jjozerg.search.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * packageName : com.jjozerg.search.keyword.adaptor.out.persistence
 * fileName : KeywordJpaEntity
 * author : joguk
 * description : 검색키워드 Jpa Entity
 */

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "TB_KEYWORD")
public class KeywordJpaEntity extends BaseEntity{
    @Id
    @Column(name = "keyword")
    private String keyword;

    @Column(name = "keyword_search_count")
    private Long keywordSearchCount;
}
