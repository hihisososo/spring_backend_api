package com.lyj.backend.divider.util;

import com.lyj.backend.divider.exception.InvalidUrlException;
import com.lyj.backend.divider.exception.ResponseBodyReadFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HttpResponseReaderTest {
    private final HttpResponseReader httpResponseReader = new HttpResponseReader();

    @DisplayName("url 을 통해 response body 가져오기 성공")
    @Test
    void successTest() {
        String body = httpResponseReader.read("https://www.naver.com");
        assertThat(body).contains("<title>NAVER</title>");
    }

    @DisplayName("없는 url 요청 시, 에러 발생")
    @Test
    void noExistUrlTest() {
        assertThrows(ResponseBodyReadFailException.class, () -> {
            String body = httpResponseReader.read("https://no.exist.url");
        });
    }

    @DisplayName("url 형식이 맞지 않을 경우, 에러 발생")
    @Test
    void invalidUrlTest() {
        assertThrows(InvalidUrlException.class, () -> {
            String body = httpResponseReader.read("htttess://www.naver.com");
        });
    }
}