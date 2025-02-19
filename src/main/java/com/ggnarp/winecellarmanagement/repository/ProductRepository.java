package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductBySupplier_NameOrderByNameAsc(String supplierName);
    List<Product> findProductByQuantityOrderByQuantityAsc(int quantity);
    List<Product> findProductByQuantityLessThanOrderByQuantityAsc(int quantity);
}
