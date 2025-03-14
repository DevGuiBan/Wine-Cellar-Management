package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ProductTypeDTO;
import com.ggnarp.winecellarmanagement.entity.ProductType;
import com.ggnarp.winecellarmanagement.repository.ProductTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    private ProductTypeDTO mapToDTO(ProductType productType) {
        ProductTypeDTO dto = new ProductTypeDTO();
        dto.setId(productType.getId());
        dto.setName(productType.getName());

        return dto;
    }

    public ProductType save(ProductTypeDTO productTypeDTO) {
        ProductType productType = new ProductType();
        if(productTypeDTO.getName().length() <=3){
            throw new IllegalArgumentException("O nome do tipo de produto deve ter pelo menos 4 letras!");
        }
        productType.setName(productTypeDTO.getName());

        return productTypeRepository.save(productType);
    }

    public List<ProductTypeDTO> listAll() {
        try{
            return productTypeRepository.findAllByOrderByNameAsc()
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
                    if(!productTypeDTO.getName().isBlank() && productTypeDTO.getName().length() >=4){
                        existingProductType.setName(productTypeDTO.getName());
                    }
                    else{
                        throw new IllegalArgumentException("O tipo de produto deve ter pelo menos 4 letras!");
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
