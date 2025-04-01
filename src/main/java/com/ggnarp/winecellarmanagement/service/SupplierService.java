package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.SupplierDTO;
import com.ggnarp.winecellarmanagement.dto.SupplierProductCountDTO;
import com.ggnarp.winecellarmanagement.entity.Supplier;
import com.ggnarp.winecellarmanagement.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.regex.*;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier save(SupplierDTO supplierDTO) {
        if (supplierRepository.existsByCnpj(supplierDTO.getCnpj())) {
            throw new IllegalArgumentException("Já há um fornecedor cadastrado com este CNPJ: " + supplierDTO.getCnpj());
        }
        if (supplierRepository.existsByEmail(supplierDTO.getEmail())) {
            throw new IllegalArgumentException("Já há um fornecedor cadastrado com este E-mail: " + supplierDTO.getEmail());
        }

        if(supplierDTO.getName().equals("Nome do Fornecedor")){
            throw new IllegalArgumentException("Insira um nome válido para o Fornecedor!");
        }

        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setCnpj(supplierDTO.getCnpj());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setObservation(supplierDTO.getObservation());
        return supplierRepository.save(supplier);
    }

    public List<SupplierDTO> listAll() {
        return supplierRepository.findAll().stream().map(supplier -> {
            SupplierDTO dto = new SupplierDTO();
            dto.setName(supplier.getName());
            dto.setEmail(supplier.getEmail());
            dto.setPhoneNumber(supplier.getPhoneNumber());
            dto.setAddress(supplier.getAddress());
            dto.setCnpj(supplier.getCnpj());
            dto.setId(supplier.getId());
            dto.setObservation(supplier.getObservation());
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
        return supplierRepository.findById(id)
                .map(existingSupplier -> {
                    if(supplierDTO.getName().equals("Nome do Fornecedor")){
                        throw new IllegalArgumentException("Insira um nome válido para o Fornecedor!");
                    }
                    if (supplierDTO.getName() != null) {
                        existingSupplier.setName(supplierDTO.getName());
                    }
                    if (supplierDTO.getAddress() != null) {
                        String regex = "^(.+?), (.+?), (\\d+), (.+)-([A-Z]{2})$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(supplierDTO.getAddress());
                        if(matcher.matches()){
                            existingSupplier.setAddress(supplierDTO.getAddress());
                        }else{
                            throw new IllegalArgumentException("O Endereço deve ser no formato Rua, Bairro, Número, Cidade-UF");
                        }
                    }

                    if (supplierDTO.getCnpj() != null) {
                        Supplier sup = supplierRepository.findByCnpj(supplierDTO.getCnpj());
                        String regex = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(supplierDTO.getCnpj());
                        if(matcher.matches()){
                            if(existingSupplier.getCnpj().equals(supplierDTO.getCnpj())){
                                if(!sup.getId().equals(existingSupplier.getId())){
                                    throw new IllegalArgumentException("Já existe um fornecedor com este CNPJ.");
                                }
                                existingSupplier.setCnpj(supplierDTO.getCnpj());
                            }

                        }else{
                            throw new IllegalArgumentException("O CNPJ deve ser no formato xxx.xxx.xxx/xxxxx-xx");
                        }
                    }

                    if (supplierDTO.getPhoneNumber() != null && !supplierDTO.getPhoneNumber().isBlank()) {
                        Supplier sup = supplierRepository.findByPhoneNumber(supplierDTO.getPhoneNumber());
                        if(supplierRepository.existsByPhoneNumber(supplierDTO.getPhoneNumber())){
                            if(!existingSupplier.getId().equals(sup.getId())){
                                throw new IllegalArgumentException("Já existe um fornecedor com este número de telefone.");
                            }
                            existingSupplier.setPhoneNumber(supplierDTO.getPhoneNumber());
                        }

                    }
                    if (supplierDTO.getEmail() != null && !supplierDTO.getEmail().isBlank()) {
                        Supplier sup = supplierRepository.findByEmail(supplierDTO.getEmail());
                        if(supplierRepository.existsByEmail(supplierDTO.getEmail())){
                            if(!sup.getId().equals(existingSupplier.getId())){
                                throw new IllegalArgumentException("Já existe um fornecedor com este e-mail.");
                            }
                            existingSupplier.setEmail(supplierDTO.getEmail());
                        }
                    }
                    if(supplierDTO.getObservation() != null){
                        existingSupplier.setObservation(supplierDTO.getObservation());
                    }

                    return supplierRepository.save(existingSupplier);
                })
                .orElseThrow(() -> new ResourceAccessException("Fornecedor com o " + id + " não encontrado"));
    }

    public Supplier getById(UUID id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor com o " + id + " não encontrado"));
    }

    public List<SupplierDTO> getSupplierByAddress(String address) {
        return supplierRepository.searchSupplierByAddress(address).stream().map(supplier -> {
            SupplierDTO dto = new SupplierDTO();
            dto.setName(supplier.getName());
            dto.setEmail(supplier.getEmail());
            dto.setPhoneNumber(supplier.getPhoneNumber());
            dto.setAddress(supplier.getAddress());
            dto.setCnpj(supplier.getCnpj());
            dto.setId(supplier.getId());
            dto.setObservation(supplier.getObservation());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<SupplierProductCountDTO> getProductCountBySupplier() {
        return supplierRepository.countProductsBySupplier();
    }

    public List<SupplierProductCountDTO> getSupplierProductCountBigThan(Long quantity) {
        List<Object[]> results = supplierRepository.countProductsBySupplierBigThan(quantity);
        return results.stream().map(row -> new SupplierProductCountDTO(
                (UUID) row[0], (String) row[1], (String) row[2],
                (String) row[3], (String) row[4], (String) row[5], (Long) row[6]
        )).collect(Collectors.toList());
    }
}
