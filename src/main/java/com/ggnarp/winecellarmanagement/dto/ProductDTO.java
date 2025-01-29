package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDTO {

    private Long id;

    @NotBlank(message = "O nome do produto é necessário para o cadastro do produto!")
    private String name;

    @NotBlank(message = "Uma descrição é necessária!")
    private String description;

    @NotBlank(message = "É preciso especificar uma quantidade!")
    @Positive
    private Integer quantity;

    @NotBlank(message = "É preciso especificar um preço!")
    @Positive
    private BigDecimal price;

    @NotNull
    private Long id_product_type;

    @NotNull
    private UUID id_supplier;

    private ProductTypeDTO productType;
    private SupplierDTO supplier;

    @Data
    public static class ProductTypeDTO {
        private Long id;
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
