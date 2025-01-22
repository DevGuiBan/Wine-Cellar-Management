package com.ggnarp.winecellarmanagement.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClassProductDTO {

    private Long id;

    @NotBlank(message = "É necessário informar o nome da classe de produtos!")
    private String name;

}
