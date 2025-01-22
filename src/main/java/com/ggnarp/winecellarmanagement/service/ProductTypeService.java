package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ProductTypeDTO;
import com.ggnarp.winecellarmanagement.entity.ClassProduct;
import com.ggnarp.winecellarmanagement.entity.ProductType;
import com.ggnarp.winecellarmanagement.repository.ClassProductRepository;
import com.ggnarp.winecellarmanagement.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;
    private final ClassProductRepository classProductRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository,ClassProductRepository classProductRepository) {
        this.productTypeRepository = productTypeRepository;
        this.classProductRepository = classProductRepository;
    }

    private ProductTypeDTO mapToDTO(ProductType productType) {
        ProductTypeDTO dto = new ProductTypeDTO();
        dto.setId(productType.getId());
        dto.setName(productType.getName());

        // Map Class Product
        ProductTypeDTO.ClassProductDTO classProductDTO = new ProductTypeDTO.ClassProductDTO();
        classProductDTO.setId_class(productType.getClassProduct().getId());
        classProductDTO.setName(productType.getClassProduct().getName());
        dto.setClassProduct(classProductDTO);

        dto.setId_class_product(productType.getClassProduct().getId());

        return dto;
    }

    public ProductType save(ProductTypeDTO productTypeDTO) {
        ProductType productType = new ProductType();
        ClassProduct classProduct = classProductRepository
                .findById(productTypeDTO.getId_class_product())
                .orElseThrow(() -> new ResourceAccessException(
                        "Tipo de Produto com este id:" + productTypeDTO.getId_class_product() + " não foi encontrado"));

        productType.setName(productTypeDTO.getName());
        productType.setClassProduct(classProduct);
        return productTypeRepository.save(productType);
    }

    public List<ProductTypeDTO> listAll() {
        try{
            return productTypeRepository.findAll()
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public ProductType update(Long id, ProductTypeDTO productTypeDTO) {
        return productTypeRepository.findById(id)
                .map(existingProductType -> {
                    if(!productTypeDTO.getName().isBlank()){
                        existingProductType.setName(productTypeDTO.getName());
                    }
                    if(productTypeDTO.getId_class_product()!=null){
                        ClassProduct classProduct = classProductRepository.findById(productTypeDTO.getId_class_product())
                                .orElseThrow(() -> new ResourceAccessException(
                                        "Classe de Produto com este id:" + productTypeDTO.getId_class_product() + " não foi encontrado"));
                        existingProductType.setClassProduct(classProduct);
                    }

                    return productTypeRepository.save(existingProductType);
                })
                .orElseThrow(() -> new ResourceAccessException("Tipo de Produto com este id:" + id + " não foi encontrado"));

    }

    public void delete(Long id) {
        if (productTypeRepository.existsById(id)) {
            productTypeRepository.deleteById(id);
        } else {
            throw new ResourceAccessException("Tipo de Produto com este id:" + id + " não foi encontrado");
        }
    }

    public ProductTypeDTO getById(Long id) {
        return productTypeRepository
                .findById(id)
                .map(this::mapToDTO).
                orElseThrow(() -> new ResourceAccessException("Tipo de Produto com este id:" + id + " não foi encontrado"));
    }

}
