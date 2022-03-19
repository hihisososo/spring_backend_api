package com.lyj.backend.divider.dto;

import com.lyj.backend.divider.strategy.Type;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor
public class DivideRequest {
    @NotNull
    private final String url;
    @NotNull
    private final Type type;
    @NotNull
    private final Integer printUnit;
}
