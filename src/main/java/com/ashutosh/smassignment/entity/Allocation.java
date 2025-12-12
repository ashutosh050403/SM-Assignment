package com.ashutosh.smassignment.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "allocations")
public class Allocation {

    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false, length = 50)
    private String orderId;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 50)
    private String partnerTxId;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal grams;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amountInINR;

    @Column(nullable = false)
    private Instant timestamp;



    public Allocation() {
    }

    public Allocation(String id, String orderId, String userId, String partnerTxId,
                      BigDecimal grams, BigDecimal amountInINR, Instant timestamp) {

        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.partnerTxId = partnerTxId;
        this.grams = grams;
        this.amountInINR = amountInINR;
        this.timestamp = timestamp;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerTxId() {
        return partnerTxId;
    }

    public void setPartnerTxId(String partnerTxId) {
        this.partnerTxId = partnerTxId;
    }

    public BigDecimal getGrams() {
        return grams;
    }

    public void setGrams(BigDecimal grams) {
        this.grams = grams;
    }

    public BigDecimal getAmountInINR() {
        return amountInINR;
    }

    public void setAmountInINR(BigDecimal amountInINR) {
        this.amountInINR = amountInINR;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}