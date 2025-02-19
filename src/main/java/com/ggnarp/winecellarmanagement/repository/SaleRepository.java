package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaleRepository extends JpaRepository<Sale, UUID> {
}
