package com.lyj.backend.controller;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    @Test
    void noUrlParameterTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("type", "TEXT")
                .param("printAmount", "1"));
        perform.andExpect(status().isBadRequest());
    }

    @Test
    void noTypeParameterTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("printAmount", "1"));
        perform.andExpect(status().isBadRequest());
    }

    @Test
    void noPrintAmountParameterTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("type", "TEXT"));
        perform.andExpect(status().isBadRequest());
    }


    @Test
    void onlyAlphabetTest() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody("ABCDEFZabcdefz")
                .addHeader("Content-Type", "application/json"));

        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("type", "TEXT")
                .param("printAmount", "1"));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("quotient").value("A"))
                .andExpect(jsonPath("remainder").value("aBbCcDdEeFfZz"));
    }

    @Test
    void onlyNumberTest() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody("1357924680")
                .addHeader("Content-Type", "application/json"));

        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("type", "TEXT")
                .param("printAmount", "1"));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("quotient").value("0"))
                .andExpect(jsonPath("remainder").value("123456789"));
    }

    @Test
    void complexInputTest() throws Exception {
        mockBackEnd.enqueue(new MockResponse()
                .setBody("a<div>Ac</div>mk12<h2>a</h2>b")
                .addHeader("Content-Type", "application/json"));

        ResultActions perform = mockMvc.perform(get("/response-divider")
                .param("url", "http://localhost:8083")
                .param("type", "TEXT")
                .param("printAmount", "5"));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("quotient").value("A1a2a"))
                .andExpect(jsonPath("remainder").value("2b2cddhhiikmvv"));
    }

}