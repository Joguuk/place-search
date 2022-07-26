package com.jjozerg.search.place.adaptor.in.web;

import com.jjozerg.search.common.annotation.WebAdaptor;
import com.jjozerg.search.domain.Places;
import com.jjozerg.search.place.adaptor.in.web.dto.PlaceSearchResDto;
import com.jjozerg.search.place.adaptor.out.PlaceMapper;
import com.jjozerg.search.place.application.port.in.PlaceSearchUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName : com.jjozerg.search.place.adaptor.in.web
 * fileName : PlaceController
 * author : joguk
 * description : 장소검색 Controller
 */

@WebAdaptor
@RequiredArgsConstructor
@RestController
@RequestMapping("/search/places/v1")
public class PlaceSearchController {
    private final PlaceSearchUseCase placeSearchUseCase;
    private final PlaceMapper placeMapper;

    @GetMapping
    public List<PlaceSearchResDto> retrievePlaceList(
            @Parameter(description = "검색어")     @RequestParam String searchWord) {
        Places places = placeSearchUseCase.retrievePlaceList(searchWord);
        return placeMapper.mapToResponseDto(places);
    }
}
