package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientDTO {

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @NotBlank(message = "E-mail is mandatory")
    @Email(message = "E-mail must be valid.")
    private String email;

    @NotBlank(message = "Phone number is mandatory.")
    @Pattern(regexp = "\\d{10,11}", message = "Phone number most contain between, 10 and 11 digits.")
    private String phone_number;

    @NotBlank(message = "Address is mandatory.")
    private String address;
}
