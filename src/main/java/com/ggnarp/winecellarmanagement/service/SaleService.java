package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.entity.Sale;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import com.ggnarp.winecellarmanagement.repository.ProductRepository;
import com.ggnarp.winecellarmanagement.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    public Sale createSale(SaleDTO saleDTO) {
        Client client = clientRepository.findById(saleDTO.getClientId())
                .orElseThrow(() -> new ResourceAccessException("Cliente não encontrado"));

        List<Product> products = saleDTO.getProductIds().stream()
                .map(productId -> productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceAccessException("Produto não encontrado")))
                .collect(Collectors.toList());

        BigDecimal totalPrice = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Sale sale = new Sale();
        sale.setClient(client);
        sale.setProducts(products);
        sale.setSaleDate(LocalDate.now());
        sale.setTotalPrice(totalPrice);

        return saleRepository.save(sale);
    }

    public List<Sale> listSales() {
        return saleRepository.findAll();
    }
}
