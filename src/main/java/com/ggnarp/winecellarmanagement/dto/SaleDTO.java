package com.ggnarp.winecellarmanagement.dto;

import com.ggnarp.winecellarmanagement.entity.PaymentMethod;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class SaleDTO {
    private Long id;
    private UUID clientId;
    private List<SaleProductDTO> products;
    private LocalDate saleDate;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private PaymentMethod paymentMethod;



    @Data
    public static class SaleProductDTO {
        private Long productId;
        private int quantity;
    }
}
