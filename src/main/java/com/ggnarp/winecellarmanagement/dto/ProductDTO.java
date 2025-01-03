package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive
    private Integer quantity;

    @Positive
    private BigDecimal price;

    @NotNull
    private UUID id_product_type;

    @NotNull
    private UUID id_supplier;

    private ProductTypeDTO productType;
    private SupplierDTO supplier;

    @Data
    public static class ProductTypeDTO {
        private UUID id;
        private String name;
    }

    @Data
    public static class SupplierDTO {
        private UUID id;
        private String name;
        private String email;
        private String phone_number;
        private String address;
        private String cnpj;
    }
}
