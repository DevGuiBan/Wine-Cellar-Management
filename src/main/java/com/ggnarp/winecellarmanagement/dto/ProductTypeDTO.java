package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductTypeDTO {

    @NotBlank(message = "The name os the type of product is required")
    private String name;
}
