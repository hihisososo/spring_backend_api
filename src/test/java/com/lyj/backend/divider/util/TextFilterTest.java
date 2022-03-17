package com.lyj.backend.divider.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class TextFilterTest {
    private final TextFilter textFilter = new TextFilter();

    @DisplayName("입력된 텍스트를 알파벳만 남긴다.")
    @ParameterizedTest
    @CsvSource({"amk3n1t039^@#&0-\",amknt", "@!%2182dj124215,dj", "zamkwj12mfk,zamkwjmfk", "285asd,asd"})
    void remainAlphabet(String text, String expected) {
        assertThat(textFilter.remainAlphabet(text)).isEqualTo(expected);
    }

    @DisplayName("입력된 텍스트를 숫자만 남긴다.")
    @ParameterizedTest
    @CsvSource({"12mltasfwq=,12", "WFASEH!#^#1357,1357", "m1k2l1 0amskld,1210", "as1bag,1"})
    void remainNumber(String text, String expected) {
        assertThat(textFilter.remainNumber(text)).isEqualTo(expected);
    }
}