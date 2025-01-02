package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDTO {

    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private Integer quantity;

    @NotBlank
    private BigDecimal price;

    private int id_product_type;

    private int id_supplier;
}
