package com.jjozerg.search.keyword.adaptor.out.persistence;

import com.jjozerg.search.common.annotation.PersistenceAdaptor;
import com.jjozerg.search.domain.Keyword;
import com.jjozerg.search.keyword.application.port.out.LoadKeywordPort;
import com.jjozerg.search.keyword.application.port.out.UpdateKeywordPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * packageName : com.jjozerg.search.keyword.adaptor.out.persistence
 * fileName : KeywordPersistenceAdaptor
 * author : joguk
 * description : 검색Keyword Persistence Adaptor
 */
@Slf4j
@RequiredArgsConstructor
@PersistenceAdaptor
public class KeywordPersistenceAdaptor implements LoadKeywordPort, UpdateKeywordPort {
    private final KeywordRepository keywordRepository;
    private final KeywordMapper keywordMapper;

    @Override
    public List<Keyword> loadPopularTenKeyword() {
        List<KeywordJpaEntity> keywordJpaEntities = keywordRepository.selectPopularTenKeyword();
        return keywordJpaEntities.stream()
                .map(keywordMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void saveKeyword(String searchWord) {
        Optional<KeywordJpaEntity> optionalKeyword = keywordRepository.findByKeyword(searchWord);

        if (optionalKeyword.isPresent()) {
            KeywordJpaEntity keywordJpaEntity = optionalKeyword.get();
            keywordJpaEntity.increaseSearchCount();
            keywordRepository.save(keywordJpaEntity);
            return;
        }

        KeywordJpaEntity keywordJpaEntity = KeywordJpaEntity.builder()
                .keyword(searchWord)
                .keywordSearchCount(1L)
                .build();

        keywordRepository.save(keywordJpaEntity);
    }
}
