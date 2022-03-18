package com.lyj.backend.divider.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TextAlternativelyMergerTest {

    private final TextAlternativelyMerger textAlternativelyMerger = new TextAlternativelyMerger();

    @DisplayName("빈 문자열이 들어오는 여러 경우에 제대로 번갈아가며 출력")
    @ParameterizedTest
    @CsvSource({",asdkljqnwt,asdkljqnwt", "amskldmas,,amskldmas", ",," , "21m4klm1256lk@%,,21m4klm1256lk@%", ",20kf;lasf,20kf;lasf"})
    public void emptyprintTest(String text1, String text2, String expected){
        if(text1 == null){text1 = "";}
        if(text2 == null){text2 = "";}
        if(expected == null){expected = "";}

        assertThat(textAlternativelyMerger.merge(text1, text2)).isEqualTo(expected);
    }

    @DisplayName("값이 있는 두개의 문자열에 대해서 제대로 번갈아가며 출력")
    @ParameterizedTest
    @CsvSource({"asmkld,21516,a2s1m5k1l6d", "amskldmas,1vs,a1mvsskldmas", "016,23,02136" , "@!%GGAS,!@GASD;,@!!@%GGAGSADS;"})
    public void printTest(String text1, String text2, String expected){
        if(text1 == null){text1 = "";}
        if(text2 == null){text2 = "";}
        if(expected == null){expected = "";}

        assertThat(textAlternativelyMerger.merge(text1, text2)).isEqualTo(expected);
    }

}