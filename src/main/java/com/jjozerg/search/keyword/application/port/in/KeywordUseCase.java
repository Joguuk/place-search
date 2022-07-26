package com.jjozerg.search.keyword.application.port.in;

import com.jjozerg.search.domain.Keyword;

import java.util.List;

/**
 * packageName : com.jjozerg.search.keyword.application.port.in
 * fileName : KeywordUseCase
 * author : joguk
 * description : KeywordUseCase
 */
public interface KeywordUseCase {
    List<Keyword> retrievePopularTenKeyword();
}
