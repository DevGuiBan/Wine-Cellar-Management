package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    @NotBlank(message = "The product needs an id")
    private Long id;

    @NotBlank(message = "The name is required")
    private String name;

    @NotBlank(message = "The description is required")
    private String description;

    @NotBlank(message = "The quantity of the product is required")
    private int quantity;

    @NotBlank(message = "The price of the product is required")
    private BigDecimal price;

    @NotBlank(message = "The product type is required")
    private int id_product_type;

    @NotBlank(message = "The supplier is required")
    private int id_supplier;
}
