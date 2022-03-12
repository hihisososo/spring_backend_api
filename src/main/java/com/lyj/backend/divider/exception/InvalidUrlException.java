package com.lyj.backend.divider.exception;

public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(Exception e) {
        super(e);
    }

    public InvalidUrlException(String message, Exception e) {
        super(message, e);
    }
}
