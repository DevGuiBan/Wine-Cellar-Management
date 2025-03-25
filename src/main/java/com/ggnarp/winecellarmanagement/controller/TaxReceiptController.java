package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.TaxReceiptDTO;
import com.ggnarp.winecellarmanagement.entity.TaxReceipt;
import com.ggnarp.winecellarmanagement.service.TaxReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sale/tax-receipt/")
public class TaxReceiptController {
    private final TaxReceiptService taxReceiptService;

    public TaxReceiptController(TaxReceiptService taxReceiptService) {
        this.taxReceiptService = taxReceiptService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTaxReceipts() {
        try{
            List<TaxReceiptDTO> trs = taxReceiptService.listAll();
            return ResponseEntity.status(HttpStatus.OK).body(trs);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar os cupons fiscais no servidor");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @GetMapping("/{saleId}")
    public ResponseEntity<?> getTaxReceiptBySaleId(@PathVariable Long saleId) {
        try{
            List<TaxReceiptDTO> trs = taxReceiptService.getBySaleId(saleId);
            return ResponseEntity.status(HttpStatus.OK).body(trs);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar o cupom fiscal no servidor");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping("/{saleId}")
    public ResponseEntity<?> registerTaxReceipt(@PathVariable Long saleId) {
        try{
            TaxReceipt tr = taxReceiptService.save(saleId);
            return ResponseEntity.status(HttpStatus.OK).body(tr);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao resgistrar o cupom fiscal no servidor!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
