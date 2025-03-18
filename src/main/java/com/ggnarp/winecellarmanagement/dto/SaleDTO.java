package com.ggnarp.winecellarmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class SaleDTO {
    private Long id;
    private UUID clientId;
    private List<Long> productIds;
    private LocalDate saleDate;
    private BigDecimal totalPrice;
}
