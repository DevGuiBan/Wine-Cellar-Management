package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductTypeDTO {

    private Long id;

    @NotBlank(message = "O nome do Produto é necessário para realizar o cadastro")
    private String name;

    @NotNull
    private Long id_class_product;

    private ClassProductDTO classProduct;

    @Data
    public static class ClassProductDTO{
        private Long id_class;
        private String name;
    }
}
