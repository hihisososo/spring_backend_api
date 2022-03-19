package com.lyj.backend.divider.service;

import com.lyj.backend.divider.dto.DivideRequest;
import com.lyj.backend.divider.dto.PrintUnit;
import com.lyj.backend.divider.strategy.Type;
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
    public DivideResult getDivideResult(DivideRequest divideRequest) {
        String responseBody = httpResponseReader.read(divideRequest.getUrl());

        //response body 에 타입 적용
        String typeAppliedText = Type.getStrategy(divideRequest.getType()).getAppliedTypeText(responseBody);

        //정렬된 알파벳과 숫자 가져오기
        String alphabets = textSorter.sort(textFilter.remainAlphabet(typeAppliedText));
        String numbers = textSorter.sort(textFilter.remainNumber(typeAppliedText));
        //알파벳 + 숫자 + 나머지 조합 생성
        String alphabetNumberMerged = textAlternativelyMerger.merge(alphabets, numbers);

        //몫, 나머지 계산 후 return
        return new DivideResult(new PrintUnit(alphabetNumberMerged, divideRequest.getPrintUnit()));

    }
}
