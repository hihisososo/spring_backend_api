package com.lyj.backend.divider.dto;

import com.lyj.backend.divider.exception.PrintUnitInvalidException;

public class PrintUnit {
    private String quotient;
    private String remainder;

    public PrintUnit(String text, Integer unit) {
        if(unit < 0){
            throw new PrintUnitInvalidException("print unit must be positive number");
        }
        int quotientLen = text.length() - (text.length() % unit);
        this.quotient = text.substring(0, quotientLen);
        this.remainder = text.substring(quotientLen);
    }

    public String getQuotient() {
        return quotient;
    }

    public String getRemainder() {
        return remainder;
    }
}
