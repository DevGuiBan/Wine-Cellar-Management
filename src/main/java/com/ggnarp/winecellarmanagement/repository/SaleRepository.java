package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.saleProducts WHERE s.id = :id")
    Optional<Sale> findByIdWithProducts(@Param("id") Long id);

    List<Sale> findBySaleDateBetween(LocalDate saleDateAfter, LocalDate saleDateBefore);
}
