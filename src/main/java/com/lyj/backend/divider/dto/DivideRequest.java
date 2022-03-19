package com.lyj.backend.divider.dto;

import com.lyj.backend.divider.strategy.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DivideRequest {
    private final String url;
    private final Type type;
    private final Integer printUnit;
}
