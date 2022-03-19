package com.lyj.backend.divider.dto;

import io.swagger.annotations.ApiModelProperty;

public class DivideResult {
    @ApiModelProperty(example = "A0a1B2")
    private String quotient;
    @ApiModelProperty(example = "b3")
    private String remainder;

    public DivideResult(PrintUnit printUnit) {
        this.quotient = printUnit.getQuotient();
        this.remainder = printUnit.getRemainder();
    }

    public String getQuotient() {
        return quotient;
    }

    public String getRemainder() {
        return remainder;
    }
}
