package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.ProductDTO;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public ResponseEntity<Product> create(@RequestBody @Valid ProductDTO productDTO) {
        try{
            Product product = productService.save(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> list() {
        try{
            List<ProductDTO> prodTypes = productService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(prodTypes);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
