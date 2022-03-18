package com.lyj.backend.service;

import com.lyj.backend.divider.domain.DivideResult;
import com.lyj.backend.divider.domain.Type;
import com.lyj.backend.divider.service.HttpResponseDividerService;
import com.lyj.backend.divider.service.HttpResponseDividerServiceImpl;
import com.lyj.backend.divider.util.HttpResponseReader;
import com.lyj.backend.divider.util.TextAlternativelyMerger;
import com.lyj.backend.divider.util.TextFilter;
import com.lyj.backend.divider.util.TextSorter;
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
  
    @Test
    public void onlyAlphabetTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("ABCDEFZabcdefz");
      
        HttpResponseDividerService service = new HttpResponseDividerServiceImpl(responseBodyReader, textFilter, textSorter, merger);
        DivideResult divideResult = service.getDivideResult("http://localhost/test", Type.TEXT, 1);
        DivideResult divideResult2 = service.getDivideResult("http://localhost/test", Type.HTML, 1);

        assertThat(divideResult.getQuotient(), is("A"));
        assertThat(divideResult.getRemainder(), is("aBbCcDdEeFfZz"));
        assertThat(divideResult2.getQuotient(), is("A"));
        assertThat(divideResult2.getRemainder(), is("aBbCcDdEeFfZz"));
    }

    @Test
    public void onlyNumberTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("1357924680");
      
        HttpResponseDividerService service = new HttpResponseDividerServiceImpl(responseBodyReader, textFilter, textSorter, merger);
        DivideResult divideResult = service.getDivideResult("http://localhost/test", Type.TEXT, 1);
        DivideResult divideResult2 = service.getDivideResult("http://localhost/test", Type.HTML, 1);

        assertThat(divideResult.getQuotient(), is("0"));
        assertThat(divideResult.getRemainder(), is("123456789"));
        assertThat(divideResult2.getQuotient(), is("0"));
        assertThat(divideResult2.getRemainder(), is("123456789"));
    }

    @Test
    public void alphabetNumberTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("abcdABCD1234");
      
        HttpResponseDividerService service = new HttpResponseDividerServiceImpl(responseBodyReader, textFilter, textSorter, merger);
        DivideResult divideResult = service.getDivideResult("http://localhost/test", Type.TEXT, 1);
        DivideResult divideResult2 = service.getDivideResult("http://localhost/test", Type.HTML, 1);

        assertThat(divideResult.getQuotient(), is("A"));
        assertThat(divideResult.getRemainder(), is("1a2B3b4CcDd"));
        assertThat(divideResult2.getQuotient(), is("A"));
        assertThat(divideResult2.getRemainder(), is("1a2B3b4CcDd"));
    }

    @Test
    public void emptyInputTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("");

        HttpResponseDividerService service = new HttpResponseDividerServiceImpl(responseBodyReader, textFilter, textSorter, merger);
        DivideResult divideResult = service.getDivideResult("http://localhost/test", Type.TEXT, 1);
        DivideResult divideResult2 = service.getDivideResult("http://localhost/test", Type.HTML, 1);

        assertThat(divideResult.getQuotient(), is(""));
        assertThat(divideResult.getRemainder(), is(""));
        assertThat(divideResult2.getQuotient(), is(""));
        assertThat(divideResult2.getRemainder(), is(""));
    }

    @Test
    public void complexInputTest() {
        when(responseBodyReader.read("http://localhost/test")).thenReturn("a<div>Ac</div>mk12<h2>a</h2>b");

        HttpResponseDividerService service = new HttpResponseDividerServiceImpl(responseBodyReader, textFilter, textSorter, merger);
        DivideResult divideResult = service.getDivideResult("http://localhost/test", Type.TEXT, 5);
        DivideResult divideResult2 = service.getDivideResult("http://localhost/test", Type.HTML, 5);

        assertThat(divideResult.getQuotient(), is("A1a2a"));
        assertThat(divideResult.getRemainder(), is("2b2cddhhiikmvv"));
        assertThat(divideResult2.getQuotient(), is("A1a2a"));
        assertThat(divideResult2.getRemainder(), is("bckm"));
    }

}
