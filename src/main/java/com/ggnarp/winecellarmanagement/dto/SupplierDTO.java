package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class SupplierDTO {

    private UUID id;

    @NotBlank(message = "The name of the supplier is required")
    private String name;

    @NotBlank(message = "The address is required")
    @Pattern(regexp = "^([^,]+),([^,]+),(\\d+),([^,]+)$",message = "The address must be in the format street,zone,number,sig")
    private String address;

    @NotBlank(message = "The phone number is required")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(9?\\d{4})-?\\d{4}$",message = "The phone number must be in the format (99) 99999-9999")
    private String phone_number;

    @NotBlank(message = "The e-mail is required")
    @Email(message = "The e-mail have is valid")
    private String email;

    @NotBlank(message = "The cpnj is required")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$",message = "The CNPJ must be in the format - 99.999.999/9999-99")
    private String cnpj;

    private String observation;

}
