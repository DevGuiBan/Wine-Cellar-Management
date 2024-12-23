package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SupplierDTO {

    @NotBlank(message = "The supplier needs an id")
    private Long id;

    @NotBlank(message = "The name of the supplier is required")
    private String name;

    @NotBlank(message = "The address is required")
    @Pattern(regexp = "^(.+)\\s+(\\d+)\\s+(.+?)\\s*-\\s*([A-Z]{2})$",message = "The address must be in the format street number city - sig")
    private String address;

    @NotBlank(message = "The phone number is required")
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(9?\\d{4})-?\\d{4}$\n",message = "The phone number must be in the format (99) 99999-9999")
    private String phone_number;

    @NotBlank(message = "The e-mail is required")
    @Email(message = "The e-mail have is valid")
    private String email;

    @NotBlank(message = "The cpnj is required")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$",message = "The CNPJ must be in the format 99.999.999/9999-99")
    private String cnpj;

}
