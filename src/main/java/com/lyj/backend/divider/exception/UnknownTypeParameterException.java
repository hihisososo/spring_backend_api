package com.lyj.backend.divider.exception;

public class UnknownTypeParameterException extends RuntimeException{
    public UnknownTypeParameterException(String e) {
        super(e);
    }

    public UnknownTypeParameterException(String message, Exception e) {
        super(message, e);
    }
}
