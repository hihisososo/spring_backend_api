package com.lyj.backend.divider.service;

import com.lyj.backend.divider.dto.PrintUnit;
import com.lyj.backend.divider.dto.Type;
import com.lyj.backend.divider.dto.DivideResult;
import com.lyj.backend.divider.util.HttpResponseReader;
import com.lyj.backend.divider.util.TextAlternativelyMerger;
import com.lyj.backend.divider.util.TextFilter;
import com.lyj.backend.divider.util.TextSorter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HttpResponseDividerServiceImpl implements HttpResponseDividerService {
    final HttpResponseReader httpResponseReader;
    final TextFilter textFilter;
    final TextSorter textSorter;
    final TextAlternativelyMerger textAlternativelyMerger;

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
        return new DivideResult(new PrintUnit(alphabetNumberMerged, printUnit));

    }
}
