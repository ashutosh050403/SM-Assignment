package com.ashutosh.smassignment.controller;

import com.ashutosh.smassignment.dto.price.PriceResponseDto;
import com.ashutosh.smassignment.dto.allocation.AllocationDto;
import com.ashutosh.smassignment.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping("/price")
    public ResponseEntity<PriceResponseDto> getPrice() {
        PriceResponseDto priceDto = partnerService.getCurrentPrice();
        return ResponseEntity.ok(priceDto);
    }

    @PostMapping("/allot")
    public ResponseEntity<AllocationDto> allotGold(@RequestBody Map<String, Object> payload) {
        AllocationDto allocation = partnerService.allotGold(payload);
        return ResponseEntity.ok(allocation);
    }
}
