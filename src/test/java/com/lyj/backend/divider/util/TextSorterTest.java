package com.lyj.backend.divider.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class TextSorterTest {

    private final TextSorter textSorter = new TextSorter();

    @DisplayName("알파벳만 있는 문자열에 대해 정렬한다")
    @ParameterizedTest
    @CsvSource({"ZabZ,abZZ", "ABCDEabcde,AaBbCcDdEe", "aAzZ,AaZz", "abmfmVKSKWLNml,abfKKLlmmmNSVW"})
    void onlyAlphabetSort(String text, String expected) {
        assertThat(textSorter.sort(text)).isEqualTo(expected);
    }

    @DisplayName("숫자만 있는 문자열에 대해 정렬한다")
    @ParameterizedTest
    @CsvSource({"15136372,11233567", "3712371,1123377", "50029125,00122559", "0000001,0000001"})
    void onlyNumberSort(String text, String expected) {
        assertThat(textSorter.sort(text)).isEqualTo(expected);
    }

    @DisplayName("빈 문자열에 대해 정렬한다")
    @Test
    void emptyTextSort() {
        assertThat(textSorter.sort("")).isEqualTo("");
    }
}