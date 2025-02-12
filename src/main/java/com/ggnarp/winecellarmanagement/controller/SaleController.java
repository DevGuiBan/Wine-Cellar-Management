package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.Sale;
import com.ggnarp.winecellarmanagement.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/sale")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSale(@PathVariable UUID id) {
        try{
            Sale sale = saleService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(sale);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao pesquisar a venda com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid SaleDTO saleDTO) {
        try{
            Sale sale = saleService.save(saleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(sale);
        }
        catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao criar venda");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        try{
            List<SaleDTO> sales = saleService.listAll();
            return ResponseEntity.status(HttpStatus.OK).body(sales);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao encontrar todas as vendas");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid SaleDTO saleDTO) {
        try{
            Sale updatedSale = saleService.update(id, saleDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSale);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao atualizar a venda com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try{
            this.saleService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao excluir venda com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
