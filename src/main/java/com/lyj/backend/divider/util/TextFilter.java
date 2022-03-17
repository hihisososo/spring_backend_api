package com.lyj.backend.divider.util;

import org.springframework.stereotype.Component;

@Component
public class TextFilter {

    private static final String NON_NUMBER_REGEX = "[^0-9]";
    private static final String NON_ALPHABET_REGEX = "[^a-zA-Z]";

    public String remainAlphabet(String text){
        return text.replaceAll(NON_ALPHABET_REGEX, "");
    }

    public String remainNumber(String text){
        return text.replaceAll(NON_NUMBER_REGEX, "");
    }

}
