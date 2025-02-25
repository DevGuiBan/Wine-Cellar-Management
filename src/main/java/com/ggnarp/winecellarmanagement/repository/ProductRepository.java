package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductBySupplier_NameOrderByNameAsc(String supplierName);

    List<Product> findProductByQuantityOrderByQuantityAsc(int quantity);

    List<Product> findProductByQuantityLessThanOrderByQuantityAsc(int quantity);

    List<Product> findProductByProductType_Name(String productTypeName);

    List<Product> findBySupplier_NameAndQuantity(String supplierName, int quantity);

    List<Product> findBySupplier_NameAndQuantityLessThan(String supplierName, int quantity);

    List<Product> findBySupplier_NameAndProductType_Name(String supplierName, String productTypeName);

    List<Product> findByQuantityAndProductType_Name(int quantity, String productTypeName);

    List<Product> findByQuantityLessThanAndProductType_Name(int quantity, String productTypeName);

    List<Product> findBySupplier_NameAndQuantityAndProductType_Name(String supplierName, int quantity, String productTypeName);

    List<Product> findBySupplier_NameAndQuantityLessThanAndProductType_Name(String supplierName, int quantity, String productTypeName);
}
