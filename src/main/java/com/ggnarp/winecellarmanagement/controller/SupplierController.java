package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.SupplierDTO;
import com.ggnarp.winecellarmanagement.dto.SupplierProductCountDTO;
import com.ggnarp.winecellarmanagement.entity.Supplier;
import com.ggnarp.winecellarmanagement.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplier(@PathVariable UUID id) {
        try{
            Supplier supplier = supplierService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(supplier);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao pesquisar o fornecedor com este id:" + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getProductCountBySupplier() {
        try{
            List<SupplierProductCountDTO> counts = supplierService.getProductCountBySupplier();
            return ResponseEntity.ok(counts);
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao pesquisar a quantidade de produtos para cada fornecedor!!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @GetMapping("/countBigThan/")
    public ResponseEntity<?> getProductCountBySupplierBigThan(@RequestParam("quantity") String quantity) {
        try{
            List<SupplierProductCountDTO> counts = supplierService.getSupplierProductCountBigThan(Long.parseLong(quantity));
            return ResponseEntity.ok(counts);
        }catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao pesquisar a quantidade de produtos para cada fornecedor!!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @GetMapping("/address/")
    public ResponseEntity<?> getSupplierByAddress(@RequestParam("address") String address) {
        try{
            List<SupplierDTO> suppliers = supplierService.getSupplierByAddress(address);
            return ResponseEntity.status(HttpStatus.OK).body(suppliers);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao pesquisar o fornecedores com este endereço!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid SupplierDTO supplierDTO) {
        try{
            Supplier supplier = supplierService.save(supplierDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
        }
        catch (Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao criar fornecedor");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping
    public ResponseEntity<?> list() {
        try{
            List<SupplierDTO> suppliers = supplierService.listAll();
            return ResponseEntity.status(HttpStatus.OK).body(suppliers);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao encontrar todos os fornecedores");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid SupplierDTO supplierDTO) {
        try{
            Supplier updatedSupplier = supplierService.update(id, supplierDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSupplier);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao atualizar o fornecedor com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try{
            this.supplierService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao excluir fornecedor com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Supplier>> searchSuppliers(@RequestParam(required = false) UUID supplierId,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String email,
                                                          @RequestParam(required = false) String cnpj) {
        List<Supplier> suppliers = supplierService.searchSuppliers(supplierId, name, email, cnpj);
        return ResponseEntity.ok(suppliers);
    }
}