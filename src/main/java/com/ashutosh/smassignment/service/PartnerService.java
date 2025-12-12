package com.ashutosh.smassignment.service;

import com.ashutosh.smassignment.dto.allocation.AllocationDto;
import com.ashutosh.smassignment.dto.price.PriceResponseDto;
import com.ashutosh.smassignment.util.CalculationUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Service
public class PartnerService {


    private static final BigDecimal PRICE_PER_GRAM = new BigDecimal("13414.98");

    public PriceResponseDto getCurrentPrice() {
        PriceResponseDto dto = new PriceResponseDto();
        dto.setPricePerGram(PRICE_PER_GRAM);
        return dto;
    }


    public AllocationDto allotGold(Map<String, Object> payload) {
        String orderId = (String) payload.get("orderId");
        String userId = payload.get("userId") != null ? payload.get("userId").toString() : null;


        BigDecimal goldValue;
        Object gv = payload.get("goldValue");
        if (gv instanceof BigDecimal) {
            goldValue = (BigDecimal) gv;
        } else if (gv instanceof Number) {
            goldValue = new BigDecimal(((Number) gv).toString());
        } else {
            goldValue = new BigDecimal(gv.toString());
        }

        BigDecimal priceLocked;
        Object pl = payload.get("pricePerGramLocked");
        if (pl == null) {
            priceLocked = PRICE_PER_GRAM;
        } else if (pl instanceof BigDecimal) {
            priceLocked = (BigDecimal) pl;
        } else if (pl instanceof Number) {
            priceLocked = new BigDecimal(((Number) pl).toString());
        } else {
            priceLocked = new BigDecimal(pl.toString());
        }

        BigDecimal grams = CalculationUtils.calculateGrams(goldValue, priceLocked);

        AllocationDto allocation = new AllocationDto();
        allocation.setAllocationId(CalculationUtils.generateAllocationId());
        allocation.setOrderId(orderId);
        allocation.setGrams(grams);
        allocation.setPartnerTxId("P-" + CalculationUtils.generateAllocationId().substring(6)); // simple partner tx id

        return allocation;
    }
}
