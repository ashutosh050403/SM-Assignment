package com.ashutosh.smassignment.service;

import com.ashutosh.smassignment.dto.payment.CreatePaymentRequestDto;
import com.ashutosh.smassignment.dto.payment.CreatePaymentResponseDto;
import com.ashutosh.smassignment.util.CalculationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;


@Service
public class PaymentService {

    private final Map<String, Map<String, Object>> payments = new HashMap<>();

    public CreatePaymentResponseDto createPaymentSession(CreatePaymentRequestDto req) {

        String paymentId = "PAY-" + System.currentTimeMillis();

        String gatewayUrl;
        switch (req.getMethod().toUpperCase()) {
            case "UPI":
                gatewayUrl = "https://mock-gateway.com/pay/upi/" + paymentId;
                break;
            case "CARD":
                gatewayUrl = "https://mock-gateway.com/pay/card/" + paymentId;
                break;
            case "NETBANKING":
                gatewayUrl = "https://mock-gateway.com/pay/netbanking/" + paymentId;
                break;
            default:
                throw new RuntimeException("Unsupported payment method");
        }

        Map<String, Object> rec = new HashMap<>();
        rec.put("paymentId", paymentId);
        rec.put("orderId", req.getOrderId());
        rec.put("amount", req.getAmount());
        rec.put("method", req.getMethod());
        rec.put("status", "CREATED");
        rec.put("createdAt", Instant.now().toString());

        payments.put(paymentId, rec);

        return new CreatePaymentResponseDto(paymentId, gatewayUrl);
    }



    public Map<String, Object> simulateSuccess(Map<String, Object> payload) {
        String paymentId = (String) payload.get("paymentId");
        String orderId = (String) payload.get("orderId");
        Object amtObj = payload.get("amount");
        double amount = amtObj instanceof Number ? ((Number) amtObj).doubleValue() : Double.parseDouble(amtObj.toString());

        Map<String, Object> webhook = new HashMap<>();
        webhook.put("paymentId", paymentId);
        webhook.put("orderId", orderId);
        webhook.put("status", "SUCCESS");
        webhook.put("paymentRef", "GWREF-" + UUID.randomUUID().toString().substring(0,8));
        webhook.put("amountInINR", amount);
        webhook.put("timestamp", Instant.now().toString());


        Map<String, Object> rec = payments.get(paymentId);
        if (rec != null) {
            rec.put("status", "SUCCESS");
            rec.put("paymentRef", webhook.get("paymentRef"));
            rec.put("paidAt", webhook.get("timestamp"));
        }


        String callback = null;
        if (payload.containsKey("merchantCallbackUrl")) {
            Object cb = payload.get("merchantCallbackUrl");
            if (cb != null) callback = cb.toString();
        }

        if (callback != null && !callback.isBlank()) {
            try {
                RestTemplate rt = new RestTemplate();
                rt.postForEntity(callback, webhook, String.class);
            } catch (Exception e) {
                System.out.println("Failed to post webhook to merchant: " + e.getMessage());
            }
        }

        return webhook;
    }

    public Optional<Map<String, Object>> findPayment(String paymentId) {
        return Optional.ofNullable(payments.get(paymentId));
    }
}
