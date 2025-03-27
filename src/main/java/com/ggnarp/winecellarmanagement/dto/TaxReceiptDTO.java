package com.ggnarp.winecellarmanagement.dto;

import com.ggnarp.winecellarmanagement.entity.Sale;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class TaxReceiptDTO {
    private UUID id;

    @NotNull(message = "O código do Cupom Fiscal não pode ser nullo ou vázio!")
    private String qrCode;

    @NotNull(message = "O id dá venda não pode ser Nulo!")
    private Long idSale;

    private SaleDTO sale;

    private String enterpriseName;

    private String address;

    private String cityState;

    private String cnpj;

    private String dateOpenCnpj;

    private String IE;

    private String hourIE;

    private String IM;

    private String CCF;

    private String CDD;

    private double tax;

}

