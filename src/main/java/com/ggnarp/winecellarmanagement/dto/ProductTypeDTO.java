package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductTypeDTO {

    @NotBlank(message = "The type of product needs an id")
    private long id;

    @NotBlank(message = "The name os the type of product is required")
    private String name;

}
