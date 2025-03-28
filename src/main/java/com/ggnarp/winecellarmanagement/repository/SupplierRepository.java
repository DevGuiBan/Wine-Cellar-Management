package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.dto.SupplierProductCountDTO;
import com.ggnarp.winecellarmanagement.entity.Supplier;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);

    @Query("SELECT s FROM Supplier s WHERE " +
            "LOWER(REPLACE(s.address, ':-', '-')) LIKE LOWER(CONCAT('%', :termo, '%')) ORDER BY s.name")
    List<Supplier> searchSupplierByAddress(@Param("termo") String termo);

    @Query("SELECT new com.ggnarp.winecellarmanagement.dto.SupplierProductCountDTO(p.supplier.name, COUNT(p)) " +
            "FROM Product p GROUP BY p.supplier.name ORDER BY p.supplier.name")
    List<SupplierProductCountDTO> countProductsBySupplier();

    @Query("SELECT p.supplier.id, p.supplier.name, p.supplier.address, p.supplier.cnpj, " +
            "p.supplier.email, p.supplier.phoneNumber, COUNT(p) " +
            "FROM Product p " +
            "GROUP BY p.supplier.id, p.supplier.name, p.supplier.address, p.supplier.cnpj, " +
            "p.supplier.email, p.supplier.phoneNumber " +
            "HAVING COUNT(p) >= :quantity " +
            "ORDER BY p.supplier.name")
    List<Object[]> countProductsBySupplierBigThan(@Param("quantity") Long quantity);


    boolean existsByPhoneNumber(String phoneNumber);

    Supplier findByCnpj(String cnpj);

    Supplier findByPhoneNumber(String phoneNumber);

    Supplier findByEmail(String email);
}
