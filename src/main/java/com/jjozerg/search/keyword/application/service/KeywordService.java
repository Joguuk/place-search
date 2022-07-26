package com.jjozerg.search.keyword.application.service;

import com.jjozerg.search.common.annotation.UseCase;
import com.jjozerg.search.domain.Keyword;
import com.jjozerg.search.keyword.application.port.in.KeywordUseCase;
import com.jjozerg.search.keyword.application.port.out.LoadKeywordPort;
import com.jjozerg.search.keyword.application.port.out.UpdateKeywordPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName : com.jjozerg.search.keyword.application.service
 * fileName : KeywordService
 * author : joguk
 * description : 검색Keyword Service
 */

@UseCase
@RequiredArgsConstructor
@Transactional
public class KeywordService implements KeywordUseCase {
    private final LoadKeywordPort loadKeywordPort;
    private final UpdateKeywordPort updateKeywordPort;

    @Override
    public List<Keyword> retrievePopularTenKeyword() {
        return loadKeywordPort.loadPopularTenKeyword();
    }

    public void saveKeyword(String searchWord) {
        updateKeywordPort.saveKeyword(searchWord);
    }
}
