package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
public class ProductTypeDTO {

    private UUID id;

    @NotBlank(message = "The name os the type of product is required")
    private String name;

}
