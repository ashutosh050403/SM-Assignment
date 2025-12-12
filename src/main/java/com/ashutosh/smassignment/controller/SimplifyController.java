package com.ashutosh.smassignment.controller;

import com.ashutosh.smassignment.dto.price.PriceResponseDto;
import com.ashutosh.smassignment.dto.order.CreateOrderRequestDto;
import com.ashutosh.smassignment.dto.order.CreateOrderResponseDto;
import com.ashutosh.smassignment.dto.payment.CreatePaymentRequestDto;
import com.ashutosh.smassignment.dto.payment.CreatePaymentResponseDto;
import com.ashutosh.smassignment.dto.payment.PaymentWebhookDto;
import com.ashutosh.smassignment.dto.allocation.AllocationDto;
import com.ashutosh.smassignment.service.PartnerService;
import com.ashutosh.smassignment.service.PaymentService;
import com.ashutosh.smassignment.service.SimplifyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/simplify")
public class SimplifyController {

    private final PartnerService partnerService;
    private final PaymentService paymentService;
    private final SimplifyService simplifyService;

    public SimplifyController(PartnerService partnerService,
                              PaymentService paymentService,
                              SimplifyService simplifyService) {
        this.partnerService = partnerService;
        this.paymentService = paymentService;
        this.simplifyService = simplifyService;
    }

    @GetMapping("/price")
    public ResponseEntity<PriceResponseDto> getPrice() {
        PriceResponseDto price = partnerService.getCurrentPrice();
        return ResponseEntity.ok(price);
    }

    @PostMapping("/order")
    public ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody CreateOrderRequestDto req) {
        CreateOrderResponseDto resp = simplifyService.createOrder(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/order/{orderId}/pay")
    public ResponseEntity<CreatePaymentResponseDto> createPaymentSession(@PathVariable("orderId") String orderId,
                                                                         @RequestBody CreatePaymentRequestDto req) {
        req.setOrderId(orderId);
        CreatePaymentResponseDto resp = paymentService.createPaymentSession(req);
        simplifyService.attachPaymentToOrder(orderId, resp.getPaymentId());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/payment/confirm")
    public ResponseEntity<Map<String, Object>> paymentConfirm(@RequestBody PaymentWebhookDto webhook) {
        Map<String, Object> result = simplifyService.handlePaymentWebhook(webhook);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/allocations/{userId}")
    public ResponseEntity<List<AllocationDto>> getAllocations(@PathVariable String userId) {
        List<AllocationDto> allocs = simplifyService.getAllocationsForUser(userId);
        return ResponseEntity.ok(allocs);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<CreateOrderResponseDto> getOrderById(@PathVariable String orderId) {
        CreateOrderResponseDto dto = simplifyService.getOrderById(orderId);
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(dto);
    }
}
