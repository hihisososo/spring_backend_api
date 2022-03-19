package com.lyj.backend.controller;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HttpResponseDividerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(8083);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @DisplayName("타입 파라미터가 잘못되었을 경우, BadRequest 응답")
    @Test
    void invalidParameterTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://www.naver.com")
                .param("type", "T")
                .param("printUnit", "1"));
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("URL 포맷이 맞지 않을 경우, BadRequest 응답")
    @Test
    void invalidUrlTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "httpps://wwwasdas.atest.ascom")
                .param("type", "TEXT")
                .param("printUnit", "1"));
        perform.andExpect(status().isBadRequest());
    }
    
    @DisplayName("없는 URL 호출하였을 경우, BadRequest 응답")
    @Test
    void bodyReadFailTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://www.smksnajzxcamsl.com")
                .param("type", "TEXT")
                .param("printUnit", "1"));
        perform.andExpect(status().isInternalServerError());
    }

    @DisplayName("URL 파라미터가 없을 경우, BadRequest 응답")
    @Test
    void noUrlParameterTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("type", "TEXT")
                .param("printUnit", "1"));
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("출력형식 파라미터가 없을 경우, BadRequest 응답")
    @Test
    void noTypeParameterTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("printUnit", "1"));
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("출력단위 파라미터가 없을 경우, BadRequest 응답")
    @Test
    void noPrintAmountParameterTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("type", "TEXT"));
        perform.andExpect(status().isBadRequest());
    }

    @DisplayName("알파벳으로만 이루어진 ResponseBody 에서 몫과 나머지를 구한다")
    @Test
    void onlyAlphabetTest() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody("ABCDEFZabcdefz")
                .addHeader("Content-Type", "application/json"));

        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("type", "TEXT")
                .param("printUnit", "1"));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("quotient").value("AaBbCcDdEeFfZz"))
                .andExpect(jsonPath("remainder").value(""));
    }

    @DisplayName("숫자로만 이루어진 ResponseBody 에서 몫과 나머지를 구한다")
    @Test
    void onlyNumberTest() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody("1357924680")
                .addHeader("Content-Type", "application/json"));

        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("type", "TEXT")
                .param("printUnit", "1"));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("quotient").value("0123456789"))
                .andExpect(jsonPath("remainder").value(""));
    }

    @DisplayName("숫자,알파벳,태그로 이루어진 ResponseBody 에서 몫과 나머지를 구한다")
    @Test
    void complexInputTest() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody("a<div>Ac</div>mk12<h2>a</h2>b")
                .addHeader("Content-Type", "application/json"));

        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("type", "TEXT")
                .param("printUnit", "5"));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("quotient").value("A1a2a2b2cddhhii"))
                .andExpect(jsonPath("remainder").value("kmvv"));
    }

}