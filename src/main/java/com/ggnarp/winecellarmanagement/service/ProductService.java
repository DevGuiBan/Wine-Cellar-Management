package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ProductDTO;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.entity.ProductType;
import com.ggnarp.winecellarmanagement.entity.Supplier;
import com.ggnarp.winecellarmanagement.repository.ProductRepository;
import com.ggnarp.winecellarmanagement.repository.ProductTypeRepository;
import com.ggnarp.winecellarmanagement.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final SupplierRepository supplierRepository;

    public ProductService(ProductRepository productRepository, ProductTypeRepository productTypeRepository,SupplierRepository supplierRepository) {
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
    }

    public Product save(ProductDTO productDTO) {
        ProductType productType = productTypeRepository
                .findById(productDTO.getId_product_type())
                .orElseThrow(() -> new ResourceAccessException(
                        "Tipo de produto com ID " + productDTO.getId_product_type() + " não encontrado"));

        Supplier supplier = supplierRepository
                .findById(productDTO.getId_supplier())
                .orElseThrow(() -> new ResourceAccessException(
                        "Fornecedor com ID " + productDTO.getId_supplier() + " não encontrado"));

        Product prod = new Product();
        prod.setName(productDTO.getName());
        prod.setDescription(productDTO.getDescription());
        prod.setPrice(productDTO.getPrice());
        prod.setQuantity(productDTO.getQuantity());
        prod.setProductType(productType);
        prod.setSupplier(supplier);

        return productRepository.save(prod);
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setId_product_type(product.getProductType().getId());
        dto.setId_supplier(product.getSupplier().getId());

        // Map ProductType
        ProductDTO.ProductTypeDTO productTypeDTO = new ProductDTO.ProductTypeDTO();
        productTypeDTO.setId(product.getProductType().getId());
        productTypeDTO.setName(product.getProductType().getName());
        dto.setProductType(productTypeDTO);

        // Map Supplier
        ProductDTO.SupplierDTO supplierDTO = new ProductDTO.SupplierDTO();
        supplierDTO.setId(product.getSupplier().getId());
        supplierDTO.setName(product.getSupplier().getName());
        supplierDTO.setEmail(product.getSupplier().getEmail());
        supplierDTO.setAddress(product.getSupplier().getAddress());
        supplierDTO.setCnpj(product.getSupplier().getCnpj());
        supplierDTO.setPhone_number(product.getSupplier().getPhone_number());
        dto.setSupplier(supplierDTO);

        return dto;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getById(Long id) {
        return productRepository
                .findById(id)
                .map(this::mapToDTO).
                orElseThrow(() -> new ResourceAccessException("Produto com ID " + id + " não encontrado."));
    }

    public Product update(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    if (productDTO.getName() != null) {
                        existingProduct.setName(productDTO.getName());
                    }
                    if (productDTO.getQuantity() != null) {
                        existingProduct.setQuantity(productDTO.getQuantity());
                    }
                    if (productDTO.getDescription() != null) {
                        existingProduct.setDescription(productDTO.getDescription());
                    }
                    if (productDTO.getPrice() != null) {
                        existingProduct.setPrice(productDTO.getPrice());
                    }
                    if (productDTO.getId_product_type() != null) {
                        ProductType productType = productTypeRepository.findById(productDTO.getId_product_type())
                                .orElseThrow(() -> new ResourceAccessException(
                                        "Tipo de produto com ID " + productDTO.getId_product_type() + " não encontrado."));
                        existingProduct.setProductType(productType);
                    }
                    if (productDTO.getId_supplier() != null) {
                        Supplier supplier = supplierRepository.findById(productDTO.getId_supplier())
                                .orElseThrow(() -> new ResourceAccessException(
                                        "Fornecedor com ID " + productDTO.getId_supplier() + " não encontrado."));
                        existingProduct.setSupplier(supplier);
                    }
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new ResourceAccessException("Produto com ID " + id + " não encontrado."));
    }

    public void delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new ResourceAccessException("Tipo de produto com esse ID " + id + " não encontrado.");
        }
    }

}
