package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.SupplierDTO;
import com.ggnarp.winecellarmanagement.entity.Supplier;
import com.ggnarp.winecellarmanagement.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable UUID id) {
        Supplier supplier = supplierService
                .getById(id);
        return ResponseEntity.ok(supplier);
    }

    @PostMapping
    public ResponseEntity<Supplier> register(@RequestBody @Valid SupplierDTO supplierDTO) {
        Supplier supplier = supplierService.save(supplierDTO);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> list() {
        List<SupplierDTO> suppliers = supplierService.listAll();
        return ResponseEntity.ok(suppliers);
    }
}