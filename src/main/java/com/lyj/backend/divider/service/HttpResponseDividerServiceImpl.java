package com.lyj.backend.divider.service;

import com.lyj.backend.divider.domain.DivideResult;
import com.lyj.backend.divider.reader.ResponseBodyReader;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;

@Service
public class HttpResponseDividerServiceImpl implements HttpResponseDividerService {
    ResponseBodyReader responseBodyReader;

    public HttpResponseDividerServiceImpl(ResponseBodyReader responseBodyReader) {
        this.responseBodyReader = responseBodyReader;
    }

    @Override
    public DivideResult getDivideResult(String url, String type, int printUnit) {
        String responseBody = responseBodyReader.read(url);

        switch (type) {
            case "HTML":
                responseBody = responseBody.replaceAll("<.*?>", "");
                break;
            case "TEXT":
                break;
        }

        //정렬된 알파벳과 숫자 가져오기
        String alphabets = sortAlphabet(remainAlphabetOnly(responseBody));
        String numbers = sortNumber(remainNumberOnly(responseBody));

        //알파벳 + 숫자 + 나머지 조합 생성
        String alphabetNumberMerged = mergeAlphabetAndNumber(alphabets, numbers);

        //몫, 나머지 계산 후 return
        return getDivideResult(alphabetNumberMerged, printUnit);

    }

    private String mergeAlphabetAndNumber(String alphabets, String numbers) {

        StringBuilder alphaNumSb = new StringBuilder();
        //알파벳 + 숫자 조합 생성
        int minIdx = Math.min(numbers.length(), alphabets.length());
        for (int i = 0; i < minIdx; i++) {
            alphaNumSb.append(alphabets.charAt(i));
            alphaNumSb.append(numbers.charAt(i));
        }
        //생성 후 남은 부분 이어붙이기
        if (numbers.length() > alphabets.length()) {
            alphaNumSb.append(numbers.substring(minIdx));
        } else if (numbers.length() < alphabets.length()) {
            alphaNumSb.append(alphabets.substring(minIdx));
        }
        return alphaNumSb.toString();
    }

    private String sortNumber(String numbers) {
        char[] numberArr = numbers.toCharArray();
        Arrays.sort(numberArr);
        return String.valueOf(numberArr);
    }

    private DivideResult getDivideResult(String alphabetNumberMerged, int printUnit) {
        if (alphabetNumberMerged.length() >= printUnit) {
            return new DivideResult(alphabetNumberMerged.substring(0, printUnit), alphabetNumberMerged.substring(printUnit));
        } else {
            return new DivideResult("", alphabetNumberMerged.toString());
        }
    }

    private String sortAlphabet(String alphabets) {
        Character[] characters = new Character[alphabets.length()];
        for (int i = 0; i < characters.length; i++) {
            characters[i] = alphabets.charAt(i);
        }

        Arrays.sort(characters, new Comparator<Character>() {
                    @Override
                    public int compare(Character o1, Character o2) {
                        return getOrderScore(o1) - getOrderScore(o2);
                    }

                    // 'A' = 65, 'a' = 66, 'B' = 67, 'b' = 68, ... 로 매핑시킨 후 정렬
                    private int getOrderScore(Character c) {
                        if ('a' <= c && c <= 'z') {
                            return 'a' + (c - 'a') * 2 - 31;
                        } else {
                            return 'A' + (c - 'A') * 2;
                        }
                    }
                }
        );

        StringBuilder sb = new StringBuilder(characters.length);
        for (Character c : characters)
            sb.append(c.charValue());
        return sb.toString();
    }

    public String remainNumberOnly(String text) {
        return text.replaceAll("[^0-9]", "");
    }

    public String remainAlphabetOnly(String text) {
        return text.replaceAll("[^A-Za-z]", "");
    }
}
