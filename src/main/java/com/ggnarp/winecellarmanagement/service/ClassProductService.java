package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ClassProductDTO;
import com.ggnarp.winecellarmanagement.entity.ClassProduct;
import com.ggnarp.winecellarmanagement.repository.ClassProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassProductService {
    private final ClassProductRepository classProductRepository;

    public ClassProductService(ClassProductRepository classProductRepository) {
        this.classProductRepository=classProductRepository;
    }

    public List<ClassProductDTO> getAllClassProducts() {
        try{
            return classProductRepository.findAll().stream().map(classProduct -> {
                ClassProductDTO dto = new ClassProductDTO();
                dto.setId(classProduct.getId());
                dto.setName(classProduct.getName());
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ClassProduct save(ClassProductDTO classProductDTO) {
        try{
            ClassProduct classProduct = new ClassProduct();
            classProduct.setName(classProductDTO.getName());
            return classProductRepository.save(classProduct);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        if (!classProductRepository.existsById(id)) {
            throw new RuntimeException("Classe de Produto com este ID " + id + " não foi encontrado");
        }
        classProductRepository.deleteById(id);
    }

    public ClassProduct update(Long id, ClassProductDTO classProductDTO) {
        return classProductRepository.findById(id)
                .map(existingClass -> {
                    if (!classProductDTO.getName().isBlank()) {
                        existingClass.setName(classProductDTO.getName());
                    }
                    return classProductRepository.save(existingClass);
                })
                .orElseThrow(() -> new ResourceAccessException("Classe de Produto com o id " + id + " não encontrado"));
    }

    public ClassProduct getById(Long id) {
        return classProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe de Produto com o id " + id + " não encontrado"));
    }

}
