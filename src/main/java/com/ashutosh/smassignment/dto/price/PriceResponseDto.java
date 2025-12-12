package com.ashutosh.smassignment.dto.price;

import java.math.BigDecimal;

public class PriceResponseDto {

    private BigDecimal pricePerGram;

    public PriceResponseDto() {}

    public PriceResponseDto(BigDecimal pricePerGram) {
        this.pricePerGram = pricePerGram;
    }

    public BigDecimal getPricePerGram() {
        return pricePerGram;
    }

    public void setPricePerGram(BigDecimal pricePerGram) {
        this.pricePerGram = pricePerGram;
    }
}