package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ReportDTO {
    @NotNull
    private List<SaleReportDTO> sales;

    @Positive
    @NotNull
    private BigDecimal avgSale;

    @Positive
    @NotNull
    private BigDecimal avgSaleTotal;

    @Data
    public static class SaleReportDTO{
        private String name;
        private int quantity;
        private BigDecimal totalSum;
    }
}
