package com.ashutosh.smassignment.service;

import com.ashutosh.smassignment.dto.allocation.AllocationDto;
import com.ashutosh.smassignment.dto.order.CreateOrderRequestDto;
import com.ashutosh.smassignment.dto.order.CreateOrderResponseDto;
import com.ashutosh.smassignment.dto.payment.PaymentWebhookDto;
import com.ashutosh.smassignment.entity.Allocation;
import com.ashutosh.smassignment.entity.Order;
import com.ashutosh.smassignment.repository.AllocationRepository;
import com.ashutosh.smassignment.repository.OrderRepository;
import com.ashutosh.smassignment.util.CalculationUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;


@Service
public class SimplifyService {

    private final PartnerService partnerService;
    private final OrderRepository orderRepository;
    private final AllocationRepository allocationRepository;

    public SimplifyService(PartnerService partnerService,
                           OrderRepository orderRepository,
                           AllocationRepository allocationRepository) {
        this.partnerService = partnerService;
        this.orderRepository = orderRepository;
        this.allocationRepository = allocationRepository;
    }


    public CreateOrderResponseDto createOrder(CreateOrderRequestDto req) {
        BigDecimal amount = BigDecimal.valueOf(req.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);


        com.ashutosh.smassignment.dto.price.PriceResponseDto priceDto = partnerService.getCurrentPrice();
        BigDecimal pricePerGram = priceDto.getPricePerGram();


        BigDecimal gst = CalculationUtils.calculateGst(amount);
        BigDecimal goldValue = CalculationUtils.calculateGoldValue(amount, gst);
        BigDecimal grams = CalculationUtils.calculateGrams(goldValue, pricePerGram);


        Order order = new Order();
        order.setId(CalculationUtils.generateOrderId());
        order.setUserId(req.getUserId());
        order.setAmountInINR(amount);
        order.setGoldValue(goldValue);
        order.setGstAmount(gst);
        order.setGrams(grams);
        order.setPricePerGramLocked(pricePerGram);
        order.setPriceLockExpiry(CalculationUtils.getPriceLockExpiry());
        order.setStatus("AWAITING_PAYMENT");
        order.setCreatedAt(Instant.now());

        orderRepository.save(order);


        CreateOrderResponseDto resp = new CreateOrderResponseDto();
        resp.setOrderId(order.getId());
        resp.setAmountInINR(order.getAmountInINR());
        resp.setGoldValue(order.getGoldValue());
        resp.setGstAmount(order.getGstAmount());
        resp.setGrams(order.getGrams());
        resp.setPricePerGram(order.getPricePerGramLocked());
        resp.setPriceLockExpiry(order.getPriceLockExpiry());

        return resp;
    }


    public void attachPaymentToOrder(String orderId, String paymentId) {
        Optional<Order> o = orderRepository.findById(orderId);
        if (o.isEmpty()) return;
        Order order = o.get();
        order.setPaymentId(paymentId);
        order.setStatus("AWAITING_PAYMENT");
        orderRepository.save(order);
    }


    public Map<String, Object> handlePaymentWebhook(PaymentWebhookDto webhook) {
        Map<String, Object> result = new HashMap<>();

        Optional<Order> optOrder = orderRepository.findById(webhook.getOrderId());
        if (optOrder.isEmpty()) {
            result.put("ok", false);
            result.put("error", "order_not_found");
            return result;
        }

        Order order = optOrder.get();


        List<Allocation> existing = allocationRepository.findByOrderId(order.getId());
        if (!existing.isEmpty()) {
            Allocation a = existing.get(0);
            result.put("ok", true);
            result.put("allocationId", a.getId());
            result.put("grams", a.getGrams());
            result.put("amountInINR", a.getAmountInINR());
            return result;
        }

        if (!"SUCCESS".equalsIgnoreCase(webhook.getStatus())) {
            order.setStatus("PAYMENT_FAILED");
            orderRepository.save(order);
            result.put("ok", false);
            result.put("error", "payment_failed");
            return result;
        }


        order.setStatus("PAID");
        orderRepository.save(order);


        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", order.getId());
        payload.put("userId", order.getUserId());
        payload.put("goldValue", order.getGoldValue());
        payload.put("pricePerGramLocked", order.getPricePerGramLocked());

        AllocationDto allocationDto = partnerService.allotGold(payload);


        Allocation alloc = new Allocation();
        alloc.setId(allocationDto.getAllocationId());
        alloc.setOrderId(order.getId());
        alloc.setUserId(order.getUserId());
        alloc.setPartnerTxId(allocationDto.getPartnerTxId());
        alloc.setGrams(allocationDto.getGrams());
        alloc.setAmountInINR(order.getGoldValue());
        alloc.setTimestamp(Instant.now());
        allocationRepository.save(alloc);


        order.setStatus("ALLOCATED");
        orderRepository.save(order);

        result.put("ok", true);
        result.put("allocationId", alloc.getId());
        result.put("grams", alloc.getGrams());
        result.put("amountInINR", alloc.getAmountInINR());

        return result;
    }


    public List<com.ashutosh.smassignment.dto.allocation.AllocationDto> getAllocationsForUser(String userId) {
        List<Allocation> allocs = allocationRepository.findByUserId(userId);
        List<com.ashutosh.smassignment.dto.allocation.AllocationDto> out = new ArrayList<>();
        for (Allocation a : allocs) {
            com.ashutosh.smassignment.dto.allocation.AllocationDto dto =
                    new com.ashutosh.smassignment.dto.allocation.AllocationDto(
                            a.getId(), a.getOrderId(), a.getGrams(), a.getPartnerTxId()
                    );
            out.add(dto);
        }
        return out;
    }

    public CreateOrderResponseDto getOrderById(String orderId) {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isEmpty()) {
            return null;
        }
        Order order = opt.get();

        CreateOrderResponseDto dto = new CreateOrderResponseDto();
        dto.setOrderId(order.getId());
        dto.setAmountInINR(order.getAmountInINR());
        dto.setGoldValue(order.getGoldValue());
        dto.setGstAmount(order.getGstAmount());
        dto.setGrams(order.getGrams());
        dto.setPricePerGram(order.getPricePerGramLocked());
        dto.setPriceLockExpiry(order.getPriceLockExpiry());

        return dto;
    }
}
