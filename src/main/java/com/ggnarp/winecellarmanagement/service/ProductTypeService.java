package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ProductTypeDTO;
import com.ggnarp.winecellarmanagement.entity.ProductType;
import com.ggnarp.winecellarmanagement.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public ProductType save(ProductTypeDTO productTypeDTO) {
        ProductType productType = new ProductType();
        productType.setName(productTypeDTO.getName());
        return productTypeRepository.save(productType);
    }

    public List<ProductTypeDTO> listAll() {
        return productTypeRepository.findAll().stream().map(prodType -> {
            ProductTypeDTO dto = new ProductTypeDTO();
            dto.setId(prodType.getId());
            dto.setName(prodType.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    public ProductType update(UUID id, ProductTypeDTO productTypeDTO) {
        return productTypeRepository.findById(id)
                .map(existingProductType -> {
                    existingProductType.setName(productTypeDTO.getName());
                    return productTypeRepository.save(existingProductType);
                })
                .orElseThrow(() -> new ResourceAccessException("ProductType with this id " + id + " not found."));

    }

    public void delete(UUID id) {
        if (productTypeRepository.existsById(id)) {
            productTypeRepository.deleteById(id);
        } else {
            throw new ResourceAccessException("ProductType with this id " + id + " not found.");
        }
    }

    public ProductType getById(UUID id) {
        return productTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("ProductType with this id " + id + " not found"));
    }

}
