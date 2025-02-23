package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeDTO {
    private UUID id;

    @NotNull(message = "O nome do funcionário não pode ser nulo")
    @NotBlank(message = "O nome do funcionário não pode ser vázio!")
    private String name;

    @NotNull(message = "A data de nascimento não pode ser nula!")
    @NotBlank(message = "A data de nascimento não pode ser vázia")
    private String date_birth;

    @NotNull(message = "O CPF não pode ser um valor nulo")
    @NotBlank(message = "O CPF não pode ser vázio!")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",message = "O CPF desse ser no formato 000.000.000-00!")
    private String cpf;

    @NotNull(message = "O endereço não pode ser nulo")
    @NotBlank(message = "O endereço não pode ser vázio")
    @Pattern(regexp = "^(.+?), (.+?), (\\d+), (.+)-([A-Z]{2})$",message = "O Endereço deve ser no formato Rua, Bairro, Número, Cidade-UF")
    private String address;

    @Email
    private String email;

    @NotNull(message = "O Número de telefone não pode ser nulo")
    @NotBlank(message = "O Número de telefone não pode ser vázio")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(9?\\d{4})-?\\d{4}$", message = "O número de telefone deve ser no formato (33) 98888-8888")
    private String phoneNumber;
}
