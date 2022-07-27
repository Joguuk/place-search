package com.jjozerg.search.common.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName : com.jjozerg.search.common.utils
 * fileName : StringUtilsTest
 * author : joguk
 * description : StringUtilsTest
 */
class StringUtilsTest {

    @Test
    @DisplayName("태그와공백 제거 유틸 메소드 테스트")
    public void WhiteSpaceAndTagText_Replaced_PlaneText() {
        //given
        String stub = "<b>롯데잠실 타워</b>";
        //when
        String replacedText = StringUtils.removeTagAndWhiteSpace(stub);

        //then
        Assertions.assertThat(replacedText).isEqualTo("롯데잠실타워");
    }
}