package com.lyj.backend.divider.strategy;

import com.lyj.backend.divider.exception.UnknownTypeParameterException;

import java.security.InvalidParameterException;

public enum Type {
    HTML("HTML"), TEXT("TEXT");

    Type(String text) {
    }

    public static TypeStrategy getStrategy(Type type) {
        switch (type) {
            case HTML:
                return new HtmlTypeStrategy();
            case TEXT:
                return new TextTypeStrategy();
            default:
                throw new UnknownTypeParameterException("unknown type parameter : " + type);
        }
    }

}


