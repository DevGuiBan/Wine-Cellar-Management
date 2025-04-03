package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class ManagerDTO {

    private UUID id;

    @NotNull(message = "O nome do gerente não pode ser nulo")
    @NotBlank(message = "O nome do gerente não pode ser vázio!")
    private String name;

    @NotNull(message = "O CPF não pode ser um valor nulo")
    @NotBlank(message = "O CPF não pode ser vázio!")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",message = "O CPF desse ser no formato 000.000.000-00!")
    private String cpf;

    @Email
    private String email;

    @NotNull(message = "A senha não pode ser nula")
    @NotBlank(message = "A senha não pode ser vazia")
    private String password;
}
