package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.UUID;

@Data
public class ClientDTO {

    private UUID id;

    @NotBlank(message = "The name is required")
    private String name;

    @NotBlank(message = "The e-mail is required")
    @Email(message = "The e-mail have is valid.")
    private String email;

    @NotBlank(message = "The phone number is required")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(9?\\d{4})-?\\d{4}$", message = "The phone number must be in the format (99) 99999-9999")
    private String phone_number;

    @NotBlank(message = "The address is required.")
    @Pattern(regexp = "^(.+)\\s+(\\d+)\\s+(.+?)\\s*-\\s*([A-Z]{2})$",message = "The address must be in the format street number city - sig")
    private String address;
}
