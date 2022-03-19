package com.lyj.backend.divider.error;

import com.lyj.backend.divider.exception.InvalidUrlException;
import com.lyj.backend.divider.exception.PrintUnitInvalidException;
import com.lyj.backend.divider.exception.ResponseBodyReadFailException;
import com.lyj.backend.divider.exception.UnknownTypeParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResponseBodyReadFailException.class)
    public ResponseEntity<ErrorResponse> handle(ResponseBodyReadFailException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "Internal Server Error", e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class, InvalidUrlException.class, PrintUnitInvalidException.class, UnknownTypeParameterException.class})
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(400, "Invalid Parameter", e.getMessage()));
    }
}
