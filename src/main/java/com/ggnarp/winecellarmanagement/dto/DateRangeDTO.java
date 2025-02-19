package com.ggnarp.winecellarmanagement.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class DateRangeDTO {
    @NotBlank(message = "A data incial não pode ser vázia!")
    private String start_date;

    @NotBlank(message = "A data final não pode ser vázia!")
    private String end_date;
}

