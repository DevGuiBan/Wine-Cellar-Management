package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ClientDTO {

    private UUID id;

    @NotBlank(message = "O nome é necessário!")
    private String name;

    @NotBlank(message = "É necessário inserir o email!")
    @Email(message = "Insira um e-mail válido!")
    private String email;

    @NotBlank(message = "O número de telefone é necessário!")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(9?\\d{4})-?\\d{4}$", message = "O número de telefone deve ser no formato (33) 98888-8888")
    private String phoneNumber;

    @NotBlank(message = "O endereço é necessário!")
    @Pattern(regexp = "^(.+?), (.+?), (\\d+), (.+)-([A-Z]{2})$",message = "O Endereço deve ser no formato Rua, Bairro, Número, Cidade-UF")
    private String address;

    @NotBlank
    private String date_brith;

    @NotBlank
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
    private String cpf;
}
