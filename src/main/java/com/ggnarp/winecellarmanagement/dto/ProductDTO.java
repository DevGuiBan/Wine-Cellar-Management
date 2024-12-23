package com.ggnarp.winecellarmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private int quantity;

    private BigDecimal price;

    private int id_product_type;

    private int id_supplier;
}
