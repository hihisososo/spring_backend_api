package com.lyj.backend.divider.strategy;

public class HtmlTypeStrategy implements TypeStrategy{
    private static final String TAG_REGEX = "<.*?>";

    @Override
    public String getAppliedTypeText(String text) {
        return text.replaceAll(TAG_REGEX, "");
    }
}