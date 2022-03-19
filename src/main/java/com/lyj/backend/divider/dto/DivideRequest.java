package com.lyj.backend.divider.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DivideRequest {
    private final String url;
    private final Type type;
    private final Integer printUnit;
}
