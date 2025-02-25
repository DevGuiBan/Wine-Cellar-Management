package com.ggnarp.winecellarmanagement.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SupplierProductCountDTO {
    private UUID supplierId;
    private String supplierName;
    private String address;
    private String cnpj;
    private String email;
    private String phoneNumber;
    private Long productCount;

    public SupplierProductCountDTO(UUID supplierId, String supplierName, String address,
                                   String cnpj, String email, String phoneNumber, Long productCount) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.address = address;
        this.cnpj = cnpj;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.productCount = productCount;
    }

    public SupplierProductCountDTO(String supplierName, Long productCount) {
        this.supplierName = supplierName;
        this.productCount = productCount;
    }

}