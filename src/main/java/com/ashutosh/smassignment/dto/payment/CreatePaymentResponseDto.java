package com.ashutosh.smassignment.dto.payment;

public class CreatePaymentResponseDto {

    private String paymentId;
    private String redirectUrl;

    public CreatePaymentResponseDto() {}

    public CreatePaymentResponseDto(String paymentId, String paymentUrl) {
        this.paymentId = paymentId;
        this.redirectUrl = paymentUrl;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
