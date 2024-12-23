package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.ProductTypeDTO;
import com.ggnarp.winecellarmanagement.entity.ProductType;
import com.ggnarp.winecellarmanagement.service.ProductTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product_type")
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @PostMapping
    public ResponseEntity<ProductType> create(@RequestBody @Valid ProductTypeDTO productTypeDTO) {
        try{
            ProductType prodType = productTypeService.save(productTypeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(prodType);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductType> getById(@PathVariable Long id) {
        try{
            ProductType productType = productTypeService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(productType);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductTypeDTO>> list() {
        try{
            List<ProductTypeDTO> prodTypes = productTypeService.listAll();
            return ResponseEntity.status(HttpStatus.OK).body(prodTypes);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductType> update(@PathVariable Long id, @RequestBody ProductTypeDTO productTypeDTO) {
        try{
            ProductType updatedProductType = productTypeService.update(id, productTypeDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProductType);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try{
            productTypeService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
