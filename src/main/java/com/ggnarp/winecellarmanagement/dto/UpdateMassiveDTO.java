package com.ggnarp.winecellarmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdateMassiveDTO {
    @NotNull
    private int quantity;
    @NotNull
    private List<Integer> productsIds;
}
