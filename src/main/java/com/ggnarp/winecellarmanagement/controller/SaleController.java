package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.Sale;
import com.ggnarp.winecellarmanagement.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody @Valid SaleDTO saleDTO) {
        try {
            Sale sale = saleService.createSale(saleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(sale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar a venda: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Sale>> listSales() {
        return ResponseEntity.ok(saleService.listSales());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Long id, @RequestBody @Valid SaleDTO saleDTO) {
        try {
            Sale updatedSale = saleService.updateSale(id, saleDTO);
            return ResponseEntity.ok(updatedSale);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao atualizar a venda: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable Long id) {
        try {
            saleService.deleteSale(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao excluir a venda: " + e.getMessage());
        }
    }
}
