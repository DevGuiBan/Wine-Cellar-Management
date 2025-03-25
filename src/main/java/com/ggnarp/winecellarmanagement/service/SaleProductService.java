package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.entity.Sale;
import com.ggnarp.winecellarmanagement.entity.SaleProduct;
import com.ggnarp.winecellarmanagement.repository.ProductRepository;
import com.ggnarp.winecellarmanagement.repository.SaleProductRepository;
import com.ggnarp.winecellarmanagement.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleProductService {

    private final SaleProductRepository saleProductRepository;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleProductService(SaleProductRepository saleProductRepository, SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleProductRepository = saleProductRepository;
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public List<SaleProduct> listAll() {
        return saleProductRepository.findAll().stream().map(sp -> {
            SaleProduct response = new SaleProduct();
            response.setId(sp.getId());
            response.setSale(sp.getSale());
            response.setProduct(sp.getProduct());
            response.setQuantity(sp.getQuantity());
            return response;
        }).collect(Collectors.toList());
    }

    public SaleProduct getById(Long id) {
        SaleProduct saleProduct = saleProductRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Item de venda não encontrado"));

        SaleProduct response = new SaleProduct();
        response.setId(saleProduct.getId());
        response.setSale(saleProduct.getSale());
        response.setProduct(saleProduct.getProduct());
        response.setQuantity(saleProduct.getQuantity());

        return response;
    }

    public SaleProduct update(Long id, SaleDTO.SaleProductDTO saleProductDTO) {
        Optional<SaleProduct> existingSaleProductOpt = saleProductRepository.findById(id);
        if (existingSaleProductOpt.isEmpty()) {
            throw new ResourceAccessException("Item de venda com ID " + id + " não encontrado.");
        }

        SaleProduct existingSaleProduct = existingSaleProductOpt.get();

        if (saleProductDTO.getQuantity() > 0) {
            Product product = existingSaleProduct.getProduct();
            int newQuantity = saleProductDTO.getQuantity();
            int difference = newQuantity - existingSaleProduct.getQuantity();

            if (product.getQuantity() < difference) {
                throw new IllegalArgumentException("Estoque insuficiente para atualizar a quantidade do produto: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - difference);
            productRepository.save(product);
            existingSaleProduct.setQuantity(newQuantity);
        }

        return saleProductRepository.save(existingSaleProduct);
    }

    public void delete(Long id) {
        if (!saleProductRepository.existsById(id)) {
            throw new ResourceAccessException("Item de venda com ID " + id + " não encontrado.");
        }

        saleProductRepository.deleteById(id);
    }
}