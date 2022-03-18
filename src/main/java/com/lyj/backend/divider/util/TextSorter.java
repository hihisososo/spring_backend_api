package com.lyj.backend.divider.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class TextSorter {

    public static final String EMPTY_REGEX = "";

    public String sort(String text) {
        return Arrays.stream(text.split(EMPTY_REGEX)).
                sorted().sorted(String.CASE_INSENSITIVE_ORDER).collect(Collectors.joining());
    }

}
