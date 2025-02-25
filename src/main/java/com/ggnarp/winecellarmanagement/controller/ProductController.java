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

    @GetMapping("/supplier/")
    public ResponseEntity<?> getProductBySupplierName(@RequestParam("s_name") String supplierName) {
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

    @GetMapping("/prodType/{t_name}")
    public ResponseEntity<?> getProductByProductType(@PathVariable("t_name") String name) {
        try{
            List<ProductDTO> product = productService.getProductByProductType(name);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com este tipo:" + name + ". Não foram encontrado!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/get/SupQua")
    public ResponseEntity<?> getProductBySuplierAndQuantity(@RequestParam("quantity") String quantity,@RequestParam("supplier") String supplierName) {
        try{
            List<ProductDTO> product = productService.getProductsBySupplierAndQuantity(supplierName, Integer.parseInt(quantity));
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com este fornecedor:" + supplierName + " e esta quantidade:" + quantity+ ". Não foram encontrados!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/get/SupLQ")
    public ResponseEntity<?> getProductBySupplierAndLowQuantity(@RequestParam("supplier") String supplierName) {
        try{
            List<ProductDTO> product = productService.getProductsBySupplierAndLowStock(supplierName);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com este fornecedor:" + supplierName + " e com baixo estoque. Não foram encontrados!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/get/SupProdT")
    public ResponseEntity<?> getProductBySupplierAndProdType(@RequestParam("supplier") String supplierName,@RequestParam("prod_t") String productTypeName) {
        try{
            List<ProductDTO> product = productService.getProductsBySupplierAndProductType(supplierName,productTypeName);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com este fornecedor:" + supplierName + " e com este tipo de produto:" + productTypeName + ". Não foram encontrados!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/get/QuaProdT")
    public ResponseEntity<?> getProductsByQuantityAndProductType(@RequestParam("quantity")String quantity,@RequestParam("prod_t") String productTypeName) {
        try{
            List<ProductDTO> product = productService.getProductsByQuantityAndProductType(Integer.parseInt(quantity),productTypeName);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com essa quantidade:" + quantity + " e com este tipo de produto:" + productTypeName + ". Não foram encontrados!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/get/LQProdT")
    public ResponseEntity<?> getProductsByLowStockAndProductType(@RequestParam("prod_t") String productTypeName) {
        try{
            List<ProductDTO> product = productService.getProductsByLowStockAndProductType(productTypeName);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com baixo estoque e com este tipo de produto:" + productTypeName + ". Não foram encontrados!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/get/SupQuaProdT")
    public ResponseEntity<?> getProductsBySupplierQuantityAndProductType(@RequestParam("supplier")String supplierName, @RequestParam("quantity") Integer quantity ,@RequestParam("prod_t") String productTypeName) {
        try{
            List<ProductDTO> product = productService.getProductsBySupplierQuantityAndProductType(supplierName,quantity,productTypeName);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com este fornecedor:" + supplierName + "com essa quantidade:" + quantity + "e com este tipo de produto:" + productTypeName + ". Não foram encontrados!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/get/SupLQProdT")
    public ResponseEntity<?> getProductsBySupplierLowStockAndProductType(@RequestParam("supplier")String supplierName ,@RequestParam("prod_t") String productTypeName) {
        try{
            List<ProductDTO> product = productService.getProductsBySupplierLowStockAndProductType(supplierName,productTypeName);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Produtos com este fornecedor:" + supplierName + "com baixo estoque e com este tipo de produto:" + productTypeName + ". Não foram encontrados!");
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
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
