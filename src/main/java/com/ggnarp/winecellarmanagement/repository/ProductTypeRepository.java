package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
