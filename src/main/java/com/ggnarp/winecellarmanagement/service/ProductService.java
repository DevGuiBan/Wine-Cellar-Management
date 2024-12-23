package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ProductDTO;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(ProductDTO productDTO) {
        Product prod = new Product();
        prod.setName(productDTO.getName());
        prod.setDescription(productDTO.getDescription());
        prod.setPrice(productDTO.getPrice());
        prod.setQuantity(productDTO.getQuantity());
        return productRepository.save(prod);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(prod->{
            ProductDTO dto = new ProductDTO();
            dto.setId(prod.getId());
            dto.setName(prod.getName());
            dto.setDescription(prod.getDescription());
            dto.setPrice(prod.getPrice());
            dto.setQuantity(prod.getQuantity());
            return dto;
        }).collect(Collectors.toList());
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("ProductType with this id " + id + " has not found."));
    }

    public Product update(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDTO.getName());
                    existingProduct.setQuantity(productDTO.getQuantity());
                    existingProduct.setDescription(productDTO.getDescription());
                    existingProduct.setPrice(productDTO.getPrice());
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new ResourceAccessException("Product with this id " + id + " not found."));

    }

    public void delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceAccessException("ProductType with this id " + id + " not found.");
        }
    }

}
