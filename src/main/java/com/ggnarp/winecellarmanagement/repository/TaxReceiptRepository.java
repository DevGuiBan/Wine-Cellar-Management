package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.TaxReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaxReceiptRepository extends JpaRepository<TaxReceipt, UUID> {
    List<TaxReceipt> findByIdSale(Long idSale);
}
