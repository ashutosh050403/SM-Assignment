package com.ashutosh.smassignment.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

public class CalculationUtils {


    private static final BigDecimal GST_RATE = new BigDecimal("0.03");


    private static final long PRICE_LOCK_DURATION_SECONDS = 300;


    public static BigDecimal calculateGst(BigDecimal amount) {
        // amount * GST_RATE
        return amount.multiply(GST_RATE).setScale(2, RoundingMode.HALF_UP);
    }



    public static BigDecimal calculateGoldValue(BigDecimal amount, BigDecimal gstAmount) {
        // goldValue = totalAmount - gstAmount
        return amount.subtract(gstAmount).setScale(2, RoundingMode.HALF_UP);
    }



    public static BigDecimal calculateGrams(BigDecimal goldValue, BigDecimal pricePerGram) {
        // grams = goldValue / pricePerGram
        return goldValue.divide(pricePerGram, 8, RoundingMode.HALF_UP);
    }


    public static Instant getPriceLockExpiry() {
        return Instant.now().plusSeconds(PRICE_LOCK_DURATION_SECONDS);
    }



    public static String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }



    public static String generateAllocationId() {
        return "ALLOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }


    public static String generatePaymentId() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}