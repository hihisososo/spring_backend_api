package com.lyj.backend.divider.error;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private final Integer status;
    private final String error;
    private final String message;
}
