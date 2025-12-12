package com.ashutosh.smassignment.dto.payment;

public class CreatePaymentRequestDto {

    private String orderId;
    private double amount;

    public CreatePaymentRequestDto() {}

    public CreatePaymentRequestDto(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
