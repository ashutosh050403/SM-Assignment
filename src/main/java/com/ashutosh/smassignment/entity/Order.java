package com.ashutosh.smassignment.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amountInINR;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal goldValue;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal gstAmount;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal grams;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal pricePerGramLocked;

    @Column(nullable = false)
    private Instant priceLockExpiry;

    @Column(length = 50)
    private String paymentId;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private Instant createdAt;



    public Order() {
    }

    public Order(String id, String userId, BigDecimal amountInINR, BigDecimal goldValue,
                 BigDecimal gstAmount, BigDecimal grams, BigDecimal pricePerGramLocked,
                 Instant priceLockExpiry, String paymentId, String status, Instant createdAt) {

        this.id = id;
        this.userId = userId;
        this.amountInINR = amountInINR;
        this.goldValue = goldValue;
        this.gstAmount = gstAmount;
        this.grams = grams;
        this.pricePerGramLocked = pricePerGramLocked;
        this.priceLockExpiry = priceLockExpiry;
        this.paymentId = paymentId;
        this.status = status;
        this.createdAt = createdAt;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public BigDecimal getPricePerGramLocked() {
        return pricePerGramLocked;
    }

    public void setPricePerGramLocked(BigDecimal pricePerGramLocked) {
        this.pricePerGramLocked = pricePerGramLocked;
    }

    public Instant getPriceLockExpiry() {
        return priceLockExpiry;
    }

    public void setPriceLockExpiry(Instant priceLockExpiry) {
        this.priceLockExpiry = priceLockExpiry;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
