package com.ashutosh.smassignment.controller;

import com.ashutosh.smassignment.dto.payment.CreatePaymentRequestDto;
import com.ashutosh.smassignment.dto.payment.CreatePaymentResponseDto;
import com.ashutosh.smassignment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreatePaymentResponseDto> createPayment(@RequestBody CreatePaymentRequestDto req) {
        CreatePaymentResponseDto resp = paymentService.createPaymentSession(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/simulate-success")
    public ResponseEntity<Map<String, Object>> simulateSuccess(@RequestBody Map<String, Object> body) {
        Map<String, Object> webhookPayload = paymentService.simulateSuccess(body);
        return ResponseEntity.ok(webhookPayload);
    }
}
