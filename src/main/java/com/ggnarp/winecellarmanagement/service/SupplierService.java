package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.SupplierDTO;
import com.ggnarp.winecellarmanagement.entity.Supplier;
import com.ggnarp.winecellarmanagement.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier save(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhone_number(supplierDTO.getPhone_number());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setCnpj(supplierDTO.getCnpj());
        return supplierRepository.save(supplier);
    }

    public List<SupplierDTO> listAll() {
        return supplierRepository.findAll().stream().map(supplier -> {
            SupplierDTO dto = new SupplierDTO();
            dto.setName(supplier.getName());
            dto.setEmail(supplier.getEmail());
            dto.setPhone_number(supplier.getPhone_number());
            dto.setAddress(supplier.getAddress());
            dto.setCnpj(supplier.getCnpj());
            dto.setId(supplier.getId());
            return dto;
        }).collect(Collectors.toList());
    }

    public void delete(UUID id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier with ID " + id + " not found");
        }
        supplierRepository.deleteById(id);
    }

    public Supplier update(UUID id, SupplierDTO supplierDTO) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier with ID " + id + " not found"));

        existingSupplier.setName(supplierDTO.getName());
        existingSupplier.setEmail(supplierDTO.getEmail());
        existingSupplier.setPhone_number(supplierDTO.getPhone_number());
        existingSupplier.setAddress(supplierDTO.getAddress());
        existingSupplier.setCnpj(supplierDTO.getCnpj());

        return supplierRepository.save(existingSupplier);
    }

    public Supplier getById(UUID id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier with ID " + id + " not found"));
    }

}
