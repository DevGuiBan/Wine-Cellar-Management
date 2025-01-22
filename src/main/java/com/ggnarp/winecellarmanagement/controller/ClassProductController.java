package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.ClassProductDTO;
import com.ggnarp.winecellarmanagement.dto.ProductDTO;
import com.ggnarp.winecellarmanagement.entity.ClassProduct;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.service.ClassProductService;
import com.ggnarp.winecellarmanagement.service.ClientService;
import com.ggnarp.winecellarmanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/class_product")
public class ClassProductController {
    private final ClassProductService classProductService;

    public ClassProductController(ClassProductService classProductService) {
        this.classProductService = classProductService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid ClassProductDTO classProductDTO) {
        try{
            ClassProduct classProduct = classProductService.save(classProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(classProduct);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Classe de Produto não foi criada ");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        try{
            List<ClassProductDTO> classProducts = classProductService.getAllClassProducts();
            return ResponseEntity.status(HttpStatus.OK).body(classProducts);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Classe de Produtos não foi encontrada");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            ClassProduct classProduct = classProductService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(classProduct);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar a Classe de produto");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClassProductDTO classProductDTO) {
        try{
            ClassProduct udatedClassProduct = classProductService.update(id, classProductDTO);
            return ResponseEntity.status(HttpStatus.OK).body(udatedClassProduct);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao atualizar a classe de produto");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try{
            classProductService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao excluir esta classe de produto");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

}
