package com.lyj.backend.service;

import com.lyj.backend.divider.dto.DivideRequest;
import com.lyj.backend.divider.dto.DivideResult;
import com.lyj.backend.divider.strategy.Type;
import com.lyj.backend.divider.service.HttpResponseDividerService;
import com.lyj.backend.divider.service.HttpResponseDividerServiceImpl;
import com.lyj.backend.divider.util.HttpResponseReader;
import com.lyj.backend.divider.util.TextAlternativelyMerger;
import com.lyj.backend.divider.util.TextFilter;
import com.lyj.backend.divider.util.TextSorter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HttpResponseDivierServiceTest {
    @Mock
    HttpResponseReader responseBodyReader;

    private final TextFilter textFilter = new TextFilter();
    private final TextSorter textSorter = new TextSorter();
    private final TextAlternativelyMerger merger = new TextAlternativelyMerger();
    private HttpResponseDividerService service;

    @BeforeEach
    void init(){
        this.service = new HttpResponseDividerServiceImpl(responseBodyReader, textFilter, textSorter, merger);
    }


    @DisplayName("알파벳만 있는 응답일 경우에 Type 별로 알맞은 결과 확인")
    @Test
    public void onlyAlphabetTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("ABCDEFZabcdefz");

        DivideResult divideResult = service.getDivideResult(new DivideRequest("http://localhost/test", Type.TEXT, 2));
        DivideResult divideResult2 = service.getDivideResult(new DivideRequest("http://localhost/test", Type.HTML, 2));

        assertThat(divideResult.getQuotient(), is("AaBbCcDdEeFfZz"));
        assertThat(divideResult.getRemainder(), is(""));
        assertThat(divideResult2.getQuotient(), is("AaBbCcDdEeFfZz"));
        assertThat(divideResult2.getRemainder(), is(""));
    }

    @DisplayName("숫자만 있는 응답일 경우에 Type 별로 알맞은 결과 확인")
    @Test
    public void onlyNumberTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("1357924680");

        DivideResult divideResult = service.getDivideResult(new DivideRequest("http://localhost/test", Type.TEXT, 3));
        DivideResult divideResult2 = service.getDivideResult(new DivideRequest("http://localhost/test", Type.HTML, 3));

        assertThat(divideResult.getQuotient(), is("012345678"));
        assertThat(divideResult.getRemainder(), is("9"));
        assertThat(divideResult2.getQuotient(), is("012345678"));
        assertThat(divideResult2.getRemainder(), is("9"));
    }

    @DisplayName("알파벳,숫자가 섞인 응답일 경우에 Type 별로 알맞은 결과 확인")
    @Test
    public void alphabetNumberTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("abcdABCD1234");

        DivideResult divideResult = service.getDivideResult(new DivideRequest("http://localhost/test", Type.TEXT, 5));
        DivideResult divideResult2 = service.getDivideResult(new DivideRequest("http://localhost/test", Type.HTML, 5));

        assertThat(divideResult.getQuotient(), is("A1a2B3b4Cc"));
        assertThat(divideResult.getRemainder(), is("Dd"));
        assertThat(divideResult2.getQuotient(), is("A1a2B3b4Cc"));
        assertThat(divideResult2.getRemainder(), is("Dd"));
    }

    @DisplayName("빈 응답일 경우에 Type 별로 알맞은 결과 확인")
    @Test
    public void emptyInputTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("");

        DivideResult divideResult = service.getDivideResult(new DivideRequest("http://localhost/test", Type.TEXT, 1));
        DivideResult divideResult2 = service.getDivideResult(new DivideRequest("http://localhost/test", Type.HTML, 1));

        assertThat(divideResult.getQuotient(), is(""));
        assertThat(divideResult.getRemainder(), is(""));
        assertThat(divideResult2.getQuotient(), is(""));
        assertThat(divideResult2.getRemainder(), is(""));
    }

    @DisplayName("여러 문자가 섞인 응답일 경우에 Type 별로 알맞은 결과 확인")
    @Test
    public void complexInputTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("a<div>Ac</div>mk12<h2>a</h2>b");

        DivideResult divideResult = service.getDivideResult(new DivideRequest("http://localhost/test", Type.TEXT, 5));
        DivideResult divideResult2 = service.getDivideResult(new DivideRequest("http://localhost/test", Type.HTML, 5));

        assertThat(divideResult.getQuotient(), is("A1a2a2b2cddhhii"));
        assertThat(divideResult.getRemainder(), is("kmvv"));
        assertThat(divideResult2.getQuotient(), is("A1a2a"));
        assertThat(divideResult2.getRemainder(), is("bckm"));
    }

}
