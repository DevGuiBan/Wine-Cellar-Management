package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {

    private UUID id;

    @NotNull
    private UUID clientId;

    private BigDecimal totalValue;

    private BigDecimal discount;

    @NotBlank(message = "Método de pagamento não pode ser nulo.")
    private String paymentMethod;
}
