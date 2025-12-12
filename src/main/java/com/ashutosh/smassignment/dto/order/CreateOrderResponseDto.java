package com.ashutosh.smassignment.dto.order;

import java.math.BigDecimal;
import java.time.Instant;

public class CreateOrderResponseDto {

    private String orderId;
    private BigDecimal amountInINR;
    private BigDecimal goldValue;
    private BigDecimal gstAmount;
    private BigDecimal grams;
    private BigDecimal pricePerGram;
    private Instant priceLockExpiry;

    public CreateOrderResponseDto() {}

    public CreateOrderResponseDto(String orderId, BigDecimal amountInINR, BigDecimal goldValue,
                                  BigDecimal gstAmount, BigDecimal grams,
                                  BigDecimal pricePerGram, Instant priceLockExpiry) {

        this.orderId = orderId;
        this.amountInINR = amountInINR;
        this.goldValue = goldValue;
        this.gstAmount = gstAmount;
        this.grams = grams;
        this.pricePerGram = pricePerGram;
        this.priceLockExpiry = priceLockExpiry;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmountInINR() {
        return amountInINR;
    }

    public void setAmountInINR(BigDecimal amountInINR) {
        this.amountInINR = amountInINR;
    }

    public BigDecimal getGoldValue() {
        return goldValue;
    }

    public void setGoldValue(BigDecimal goldValue) {
        this.goldValue = goldValue;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getGrams() {
        return grams;
    }

    public void setGrams(BigDecimal grams) {
        this.grams = grams;
    }

    public BigDecimal getPricePerGram() {
        return pricePerGram;
    }

    public void setPricePerGram(BigDecimal pricePerGram) {
        this.pricePerGram = pricePerGram;
    }

    public Instant getPriceLockExpiry() {
        return priceLockExpiry;
    }

    public void setPriceLockExpiry(Instant priceLockExpiry) {
        this.priceLockExpiry = priceLockExpiry;
    }
}
