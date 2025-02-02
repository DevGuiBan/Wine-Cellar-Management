package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    List<ProductType> findAllByOrderByNameAsc();
}
