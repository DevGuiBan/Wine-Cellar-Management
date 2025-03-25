package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProduct, Long> {
}
