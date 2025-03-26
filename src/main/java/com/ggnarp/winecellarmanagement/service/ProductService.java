package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ProductDTO;
import com.ggnarp.winecellarmanagement.dto.UpdateMassiveDTO;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.entity.ProductType;
import com.ggnarp.winecellarmanagement.entity.Supplier;
import com.ggnarp.winecellarmanagement.repository.ProductRepository;
import com.ggnarp.winecellarmanagement.repository.ProductTypeRepository;
import com.ggnarp.winecellarmanagement.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
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

    public List<ProductDTO> getProductByQuantity(int quantity){
        return productRepository.findProductByQuantityOrderByQuantityAsc(quantity)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductLessThan(){
        return productRepository.findProductByQuantityLessThanOrderByQuantityAsc(5)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductBySupplierName(String supplierName){
        return productRepository.findProductBySupplier_NameOrderByNameAsc(supplierName)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductByProductType(String productType){
        return productRepository.findProductByProductType_Name(productType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySupplierAndQuantity(String supplierName, int quantity) {
        return productRepository.findBySupplier_NameAndQuantity(supplierName, quantity)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySupplierAndLowStock(String supplierName) {
        return productRepository.findBySupplier_NameAndQuantityLessThan(supplierName, 5)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySupplierAndProductType(String supplierName, String productType) {
        return productRepository.findBySupplier_NameAndProductType_Name(supplierName, productType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByQuantityAndProductType(int quantity, String productType) {
        return productRepository.findByQuantityAndProductType_Name(quantity, productType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByLowStockAndProductType(String productType) {
        return productRepository.findByQuantityLessThanAndProductType_Name(5, productType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySupplierQuantityAndProductType(String supplierName, int quantity, String productType) {
        return productRepository.findBySupplier_NameAndQuantityAndProductType_Name(supplierName, quantity, productType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsBySupplierLowStockAndProductType(String supplierName, String productType) {
        return productRepository.findBySupplier_NameAndQuantityLessThanAndProductType_Name(supplierName, 5, productType)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void updateStockMassive(UpdateMassiveDTO updateDTO){
        try{
            for(Integer idProd:updateDTO.getProductsIds()){
                Product prod = productRepository.findById(idProd.longValue());
                int quantity = updateDTO.getQuantity()+prod.getQuantity();
                prod.setQuantity(quantity);
                productRepository.save(prod);
            }
        } catch (Exception e) {
            throw new ResourceAccessException("Erro ao atualizar o estoque dos produtos!");
        }
    }
}
