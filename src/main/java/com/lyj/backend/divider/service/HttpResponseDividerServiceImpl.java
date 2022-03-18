package com.lyj.backend.divider.service;

import com.lyj.backend.divider.domain.Type;
import com.lyj.backend.divider.domain.DivideResult;
import com.lyj.backend.divider.util.HttpResponseReader;
import com.lyj.backend.divider.util.TextAlternativelyMerger;
import com.lyj.backend.divider.util.TextFilter;
import com.lyj.backend.divider.util.TextSorter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class HttpResponseDividerServiceImpl implements HttpResponseDividerService {
    HttpResponseReader httpResponseReader;
    TextFilter textFilter;
    TextSorter textSorter;
    TextAlternativelyMerger textAlternativelyMerger;

    public HttpResponseDividerServiceImpl(HttpResponseReader httpResponseReader, TextFilter textFilter, TextSorter textSorter, TextAlternativelyMerger textAlternativelyMerger) {
        this.httpResponseReader = httpResponseReader;
        this.textFilter = textFilter;
        this.textSorter = textSorter;
        this.textAlternativelyMerger = textAlternativelyMerger;
    }

    @Override
    public DivideResult getDivideResult(String url, Enum<Type> type, int printUnit) {
        String responseBody = httpResponseReader.read(url);

        if (type == Type.HTML) {
            responseBody = responseBody.replaceAll("<.*?>", "");
        }

        //정렬된 알파벳과 숫자 가져오기
        String alphabets = textSorter.sort(textFilter.remainAlphabet(responseBody));
        String numbers = textSorter.sort(textFilter.remainNumber(responseBody));
        //알파벳 + 숫자 + 나머지 조합 생성
        String alphabetNumberMerged = textAlternativelyMerger.merge(alphabets, numbers);

        //몫, 나머지 계산 후 return
        return getDivideResult(alphabetNumberMerged, printUnit);

    }

    private DivideResult getDivideResult(String alphabetNumberMerged, int printUnit) {
        if (alphabetNumberMerged.length() >= printUnit) {
            return new DivideResult(alphabetNumberMerged.substring(0, printUnit), alphabetNumberMerged.substring(printUnit));
        } else {
            return new DivideResult("", alphabetNumberMerged.toString());
        }
    }

    public String remainNumberOnly(String text) {
        return text.replaceAll("[^0-9]", "");
    }

    public String remainAlphabetOnly(String text) {
        return text.replaceAll("[^A-Za-z]", "");
    }
}
