package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.SaleProduct;
import com.ggnarp.winecellarmanagement.service.SaleProductService;
import com.ggnarp.winecellarmanagement.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-product")
public class SaleProductController {

    private final SaleProductService saleProductService;
    private final SaleService saleService;

    public SaleProductController(SaleProductService saleProductService, SaleService saleService) {
        this.saleProductService = saleProductService;
        this.saleService = saleService;
    }

    @GetMapping
    public ResponseEntity<List<SaleProduct>> listAll() {
        return ResponseEntity.ok(saleProductService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleProduct> getById(@PathVariable Long id) {
        return ResponseEntity.ok(saleProductService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleProduct> update(@PathVariable Long id, @RequestBody @Valid SaleDTO.SaleProductDTO saleProductDTO) {
        return ResponseEntity.ok(saleProductService.update(id, saleProductDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleProductService.delete(id);
        return ResponseEntity.noContent().build();
    }
}