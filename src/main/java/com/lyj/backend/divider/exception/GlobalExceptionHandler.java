package com.lyj.backend.divider.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public Map<String, Object> handle(ResponseBodyReadFailException e) {
        log.error(e.getMessage(), e);
        LinkedHashMap<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("status", 500);
        errorAttributes.put("error", "Internal Server Error");
        errorAttributes.put("message", e.getMessage());
        return errorAttributes;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class, InvalidUrlException.class, PrintUnitInvalidException.class, UnknownTypeParameterException.class})
    public Map<String, Object> handle(Exception e) {
        log.error(e.getMessage(), e);
        LinkedHashMap<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("status", 400);
        errorAttributes.put("error", "Invalid Parameter");
        errorAttributes.put("message", e.getMessage());
        return errorAttributes;
    }
}
