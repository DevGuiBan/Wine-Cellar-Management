package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class SupplierDTO {

    private UUID id;

    @NotBlank(message = "O Nome do Fornecedor é necessário para realizar o cadastro!")
    private String name;

    @NotBlank(message = "O endereço é obrigatório")
    @Pattern(regexp = "^(.+?), (.+?), (\\d+), (.+)-([A-Z]{2})$",message = "O Endereço deve ser no formato Rua, Bairro, Número, Cidade-UF")
    private String address;

    @NotBlank(message = "O número de telefone é necessário")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(9?\\d{4})-?\\d{4}$",message = "O número de telefone deve ser no formato (99) 99999-9999")
    private String phone_number;

    @NotBlank(message = "É necessário um e-mail para realizar o cadastro!")
    @Email(message = "O e-mail precisa ser válido")
    private String email;

    @NotBlank(message = "O CNPJ é necessário para relaizar o cadastro!")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$",message = "O CNPJ deve ser no formato - 99.999.999/9999-99")
    private String cnpj;

    private String observation;

}
