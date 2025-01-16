package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
public class ProductTypeDTO {

    private Long id;

    @NotBlank(message = "O nome do Produto é necessário para realizar o cadastro")
    private String name;

}
