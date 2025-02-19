package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.ProductDTO;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ProductDTO productDTO) {
        try{
            Product product = productService.save(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produto não criado");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        try{
            List<ProductDTO> prodTypes = productService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(prodTypes);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos não encontrados");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/supplier/{s_name}")
    public ResponseEntity<?> getProductBySupplierName(@PathVariable("s_name") String supplierName) {
        try{
            List<ProductDTO> product = productService.getProductBySupplierName(supplierName);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com este fornecedor:" + supplierName + ". Não foi encontrado!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/quantity/{quantity}")
    public ResponseEntity<?> getProductByQuantity(@PathVariable String quantity) {
        try{
            List<ProductDTO> product = productService.getProductByQuantity(Integer.parseInt(quantity));
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com essas quantidades: "+quantity+". Não foram encontrados!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/quantity")
    public ResponseEntity<?> getProductQuantityLess() {
        try{
            List<ProductDTO> product = productService.getProductLessThan();
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ocorreu um erro ao tentar buscar os produtos com estoque baixo!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            ProductDTO product = productService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro na pesquisa do produto");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try{
            Product updatedProduct = productService.update(id, productDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro na atualização do produto");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            productService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao excluir o produto ");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
