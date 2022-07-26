package com.jjozerg.search.keyword.adaptor.in.web;

import com.jjozerg.search.domain.Keyword;
import com.jjozerg.search.keyword.adaptor.out.persistence.KeywordMapper;
import com.jjozerg.search.keyword.adaptor.out.persistence.dto.KeywordPopularResDto;
import com.jjozerg.search.keyword.application.port.in.KeywordUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : com.jjozerg.search.keyword.adaptor.in.web
 * fileName : KeywordController
 * author : joguk
 * description : Keyword Controller
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/search/keywords")
public class KeywordController {
    private final KeywordUseCase keywordUseCase;
    private final KeywordMapper keywordMapper;

    @GetMapping("/v1/popular-ten")
    public List<KeywordPopularResDto> retrievePopularTenKeyword() {
        List<Keyword> keywords = keywordUseCase.retrievePopularTenKeyword();
        return keywords.stream()
                .map(keywordMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }
}
