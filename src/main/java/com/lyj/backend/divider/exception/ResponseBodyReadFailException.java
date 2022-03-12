package com.lyj.backend.divider.exception;

public class ResponseBodyReadFailException extends RuntimeException {
    public ResponseBodyReadFailException(Exception e) {
        super(e);
    }

    public ResponseBodyReadFailException(String message, Exception e) {
        super(message, e);
    }
}
