package com.ashutosh.smassignment.dto.allocation;

import java.math.BigDecimal;

public class AllocationDto {

    private String allocationId;
    private String orderId;
    private BigDecimal grams;
    private String partnerTxId;

    public AllocationDto() {}

    public AllocationDto(String allocationId, String orderId, BigDecimal grams, String partnerTxId) {
        this.allocationId = allocationId;
        this.orderId = orderId;
        this.grams = grams;
        this.partnerTxId = partnerTxId;
    }

    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getGrams() {
        return grams;
    }

    public void setGrams(BigDecimal grams) {
        this.grams = grams;
    }

    public String getPartnerTxId() {
        return partnerTxId;
    }

    public void setPartnerTxId(String partnerTxId) {
        this.partnerTxId = partnerTxId;
    }
}
