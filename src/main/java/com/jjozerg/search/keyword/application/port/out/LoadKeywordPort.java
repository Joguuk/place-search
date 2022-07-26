package com.jjozerg.search.keyword.application.port.out;

import com.jjozerg.search.domain.Keyword;

import java.util.List;

/**
 * packageName : com.jjozerg.search.keyword.application.port.out
 * fileName : LoadKeywordPort
 * author : joguk
 * description : Keyword 검색 Port
 */
public interface LoadKeywordPort {
    List<Keyword> loadPopularTenKeyword();
}
