package com.lyj.backend.divider.exception;

public class PrintUnitInvalidException extends RuntimeException {
    public PrintUnitInvalidException(Exception e) {
        super(e);
    }

    public PrintUnitInvalidException(String message, Exception e) {
        super(message, e);
    }

    public PrintUnitInvalidException(String message) {
        super(message);
    }
}
