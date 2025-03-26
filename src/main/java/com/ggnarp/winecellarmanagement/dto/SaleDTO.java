package com.ggnarp.winecellarmanagement.dto;

import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class SaleDTO {
    @Null
    private Long id;

    private UUID clientId;

    private Client client;

    private List<SaleProductDTO> products;

    private LocalDate saleDate;

    private BigDecimal totalPrice;

    private BigDecimal discount;

    private PaymentMethod paymentMethod;

    @Data
    public static class SaleProductDTO {
        private Long productId;
        private int quantity;
        private String name;
        private BigDecimal price;
    }
}
