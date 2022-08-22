package com.jjozerg.search.keyword.adaptor.out.persistence;

import com.jjozerg.search.domain.Keyword;
import com.jjozerg.search.keyword.application.port.out.LoadKeywordPort;
import com.jjozerg.search.keyword.application.port.out.UpdateKeywordPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class KeywordPersistenceAdaptorTest {
    @Autowired
    UpdateKeywordPort updateKeywordPort;
    @Autowired
    LoadKeywordPort loadKeywordPort;

    @Test
    @DisplayName("키워드 저장 동시성 테스트")
    public void save_keyword_not_issue_concurrency() throws InterruptedException {
        //given
        String keyword = "송파";
        int nThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Callable<Void>> callableList = getCallableList(keyword, nThreads);
        executorService.invokeAll(callableList);

        //when
        List<Keyword> keywords = loadKeywordPort.loadPopularTenKeyword();

        //then
        assertThat(keywords.size()).isEqualTo(1);
        assertThat(keywords.get(0).getKeywordSearchCount()).isEqualTo(nThreads);
    }

    private List<Callable<Void>> getCallableList(String keyword, int nThreads) {
        List<Callable<Void>> callableList = new ArrayList<>();

        for (int i = 0; i < nThreads; i++) {
            callableList.add(() -> callSaveKeyword(keyword));
        }
        return callableList;
    }

    private Void callSaveKeyword(String keyword) {
        updateKeywordPort.saveKeyword(keyword);
        return null;
    }
}