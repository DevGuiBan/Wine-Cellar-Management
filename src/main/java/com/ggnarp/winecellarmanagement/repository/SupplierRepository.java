package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);

    List<Supplier> findSupplierByAddressOrderByAddressAsc(String address);
}
