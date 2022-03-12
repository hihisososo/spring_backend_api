package com.lyj.backend.divider.domain;

public class DivideResult {
    private String quotient;
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
