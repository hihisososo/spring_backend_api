package com.lyj.backend.divider.util;

import org.springframework.stereotype.Component;

@Component
public class TextAlternativelyMerger {
    public String merge(String text1, String text2) {
        StringBuilder alphaNumSb = new StringBuilder();
        //알파벳 + 숫자 조합 생성
        int minIdx = Math.min(text1.length(), text2.length());
        for (int i = 0; i < minIdx; i++) {
            alphaNumSb.append(text1.charAt(i));
            alphaNumSb.append(text2.charAt(i));
        }
        //생성 후 남은 부분 이어붙이기
        if (text1.length() > text2.length()) {
            return alphaNumSb.append(text1.substring(minIdx)).toString();
        } else if (text1.length() < text2.length()) {
            alphaNumSb.append(text2.substring(minIdx));
        }
        return alphaNumSb.toString();
    }

}
