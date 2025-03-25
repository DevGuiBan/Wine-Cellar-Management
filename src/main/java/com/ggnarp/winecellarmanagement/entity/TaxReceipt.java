package com.ggnarp.winecellarmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tax_receipt")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true,name = "id_sale")
    private Long idSale;

    @Column(nullable = false,unique = true,name = "qr_code",length = 49)
    private String qrCode;
}
