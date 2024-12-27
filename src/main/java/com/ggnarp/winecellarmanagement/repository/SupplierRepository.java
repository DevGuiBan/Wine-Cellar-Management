package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}
