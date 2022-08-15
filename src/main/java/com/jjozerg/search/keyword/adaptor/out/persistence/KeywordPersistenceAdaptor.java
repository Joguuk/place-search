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

    /**
     * 가장 많이 검색된 10개 키워드를 조회하여 반환한다.
     *
     * @author joguk
     * @date 2022/07/26 11:09 오후
     */
    @Override
    public List<Keyword> loadPopularTenKeyword() {
        List<KeywordJpaEntity> keywordJpaEntities = keywordRepository.selectPopularTenKeyword();
        return keywordJpaEntities.stream()
                .map(keywordMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    /**
     * 키워드가 있는 경우 검색 횟수를 1 증가시키고, 없는 경우 새롭게 저장한다.
     *
     * @author joguk
     * @date 2022/07/26 11:09 오후
     */
    @Override
    public void saveKeyword(String searchWord) {
        Optional<KeywordJpaEntity> optionalKeyword = keywordRepository.findByKeyword(searchWord);

        optionalKeyword.ifPresentOrElse(keywordRepository::increaseSearchCount, () -> createKeyword(searchWord));
    }

    /**
     * 키워드를 생성한다.
     *
     * @author joguk
     * @date 2022/07/26 11:10 오후
     */
    private void createKeyword(String searchWord) {
        KeywordJpaEntity keywordJpaEntity = KeywordJpaEntity.builder()
                .keyword(searchWord)
                .keywordSearchCount(1L)
                .build();

        keywordRepository.save(keywordJpaEntity);
    }
}
