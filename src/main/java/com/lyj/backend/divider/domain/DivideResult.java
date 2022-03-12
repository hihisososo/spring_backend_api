package com.lyj.backend.divider.domain;

import io.swagger.annotations.ApiModelProperty;

public class DivideResult {
    @ApiModelProperty(example = "A0a1B2")
    private String quotient;
    @ApiModelProperty(example = "b3")
    private String remainder;

    public DivideResult(String quotient, String remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }

    public String getQuotient() {
        return quotient;
    }

    public String getRemainder() {
        return remainder;
    }

    @Override
    public String toString() {
        return "DivideResult{" +
                "quotient='" + quotient + '\'' +
                ", remainder='" + remainder + '\'' +
                '}';
    }
}
