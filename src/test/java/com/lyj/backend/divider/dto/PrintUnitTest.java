package com.lyj.backend.divider.dto;

import com.lyj.backend.divider.exception.PrintUnitInvalidException;
import com.lyj.backend.divider.exception.ResponseBodyReadFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PrintUnitTest {

    @DisplayName("0 으로 나누었을 경우, ArithmeticException 발생")
    @Test
    public void arithmeticExceptionTets() {
        assertThrows(ArithmeticException.class, () -> {
            final PrintUnit printUnit = new PrintUnit("abcdefghijk", 0);
        });
    }

    @DisplayName("1로 나누었을 때, 나머지는 없다")
    @Test
    public void divideOneTest() {
        final PrintUnit printUnit = new PrintUnit("abcdefghijk", 1);

        assertThat(printUnit.getQuotient()).isEqualTo("abcdefghijk");
        assertThat(printUnit.getRemainder()).isEqualTo("");
    }

    @DisplayName("길이가 10인 문자열, 출력단위 3일경우 나머지는 1이다")
    @Test
    public void divideThreeTest() {
        final PrintUnit printUnit = new PrintUnit("abcdefghij", 3);

        assertThat(printUnit.getQuotient()).isEqualTo("abcdefghi");
        assertThat(printUnit.getRemainder()).isEqualTo("j");
    }

    @DisplayName("길이가 10인 문자열, 출력단위 2일경우 나머지는 0이다")
    @Test
    public void divideTwoTest() {
        final PrintUnit printUnit = new PrintUnit("abcdefghij", 2);

        assertThat(printUnit.getQuotient()).isEqualTo("abcdefghij");
        assertThat(printUnit.getRemainder()).isEqualTo("");
    }

    @DisplayName("문자열 길이보다 출력단위가 클 경우, 나머지는 전부이다")
    @Test
    public void largePrintUnitTest() {
        final PrintUnit printUnit = new PrintUnit("abcdefghij", Integer.MAX_VALUE);

        assertThat(printUnit.getQuotient()).isEqualTo("");
        assertThat(printUnit.getRemainder()).isEqualTo("abcdefghij");
    }

    @DisplayName("출력단위가 음수일 경우, 에러 발생")
    @Test
    public void negativeIntegerTest() {
        assertThrows(PrintUnitInvalidException.class, () -> {
            final PrintUnit printUnit = new PrintUnit("abcdefghijk", -1);
        });
    }

    @DisplayName("문자열 길이와 출력단위가 같을 경우, 나머지는 없다")
    @Test
    public void equalTextLengthAndPrintUnitTest() {
        final PrintUnit printUnit = new PrintUnit("abcdefghij", 10);

        assertThat(printUnit.getQuotient()).isEqualTo("abcdefghij");
        assertThat(printUnit.getRemainder()).isEqualTo("");
    }

}