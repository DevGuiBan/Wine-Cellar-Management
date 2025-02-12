package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.entity.PaymentMethod;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.entity.Sale;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import com.ggnarp.winecellarmanagement.repository.ProductRepository;
import com.ggnarp.winecellarmanagement.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public Sale save(SaleDTO saleDTO) {

        Client client = clientRepository.findById(saleDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + saleDTO.getClientId() + " não encontrado"));

        Product product = productRepository.findById(saleDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Produto com ID " + saleDTO.getProductId() + " não encontrado"));

        if (product.getQuantity() < saleDTO.getQuantity()) {
            throw new RuntimeException("Estoque insuficiente para o produto " + product.getName());
        }

        product.setQuantity(product.getQuantity() - saleDTO.getQuantity());
        productRepository.save(product);

        Sale sale = new Sale();
        sale.setClient(client);
        sale.setProduct(product);
        sale.setQuantity(saleDTO.getQuantity());

        // Calcula o valor total sem desconto
        BigDecimal totalValue = product.getPrice().multiply(BigDecimal.valueOf(saleDTO.getQuantity()));

        // Calcula desconto em porcentagem
        BigDecimal discountPercentage = saleDTO.getDiscount() != null ? saleDTO.getDiscount() : BigDecimal.ZERO;
        if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new RuntimeException("O desconto deve estar entre 0% e 100%.");
        }

        BigDecimal discount = totalValue.multiply(discountPercentage).divide(BigDecimal.valueOf(100));

        // Verifica se a data da compra é o aniversário do cliente e aplica 5% extra
        LocalDate today = LocalDate.now();
        if (client.getDate_brith() != null && client.getDate_brith().getMonth() == today.getMonth() &&
                client.getDate_brith().getDayOfMonth() == today.getDayOfMonth()) {
            BigDecimal birthdayDiscount = totalValue.multiply(BigDecimal.valueOf(5)).divide(BigDecimal.valueOf(100));
            discount = discount.add(birthdayDiscount);
        }

        BigDecimal finalValue = totalValue.subtract(discount);
        sale.setTotalValue(finalValue);
        sale.setDiscount(discount);

        try {
            sale.setPaymentMethod(PaymentMethod.valueOf(saleDTO.getPaymentMethod()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Método de pagamento inválido: " + saleDTO.getPaymentMethod());
        }

        sale.setPurchaseDate(LocalDateTime.now());

        return saleRepository.save(sale);
    }

    public List<SaleDTO> listAll() {
        return saleRepository.findAll().stream().map(sale -> {
            SaleDTO dto = new SaleDTO();
            dto.setId(sale.getId());
            dto.setClientId(sale.getClient().getId());
            dto.setProductId(sale.getProduct().getId());
            dto.setQuantity(sale.getQuantity());
            dto.setTotalValue(sale.getTotalValue());
            dto.setDiscount(sale.getDiscount());
            dto.setPaymentMethod(sale.getPaymentMethod().name());
            dto.setPurchaseDate(sale.getPurchaseDate());

            return dto;
        }).collect(Collectors.toList());
    }

    public void delete(UUID id) {
        if (!saleRepository.existsById(id)) {
            throw new RuntimeException("Venda com o ID " + id + " não encontrada");
        }
        saleRepository.deleteById(id);
    }

    public Sale update(UUID id, SaleDTO saleDTO) {

        Client client = clientRepository.findById(saleDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + saleDTO.getClientId() + " não encontrado"));

        Product product = productRepository.findById(saleDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Produto com ID " + saleDTO.getProductId() + " não encontrado"));

        return saleRepository.findById(id)
                .map(existingSale -> {
                    if (saleDTO.getClientId() != null) {
                        existingSale.setClient(client);
                    }
                    if (saleDTO.getProductId() != null) {
                        existingSale.setProduct(product);
                    }
                    if (saleDTO.getQuantity() != null) {
                        existingSale.setQuantity(saleDTO.getQuantity());
                    }
                    if (saleDTO.getTotalValue() != null) {
                        existingSale.setTotalValue(saleDTO.getTotalValue());
                    }
                    if (saleDTO.getDiscount() != null) {
                        existingSale.setDiscount(saleDTO.getDiscount());
                    }
                    if (saleDTO.getPaymentMethod() != null) {
                        try {
                            existingSale.setPaymentMethod(PaymentMethod.valueOf(saleDTO.getPaymentMethod()));
                        } catch (IllegalArgumentException e) {
                            throw new RuntimeException("Método de pagamento inválido: " + saleDTO.getPaymentMethod());
                        }
                    }
                    return saleRepository.save(existingSale);
                })
                .orElseThrow(() -> new ResourceAccessException("Venda com o ID " + id + " não encontrada"));
    }

    public Sale getById(UUID id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda com o ID " + id + " não encontrada"));
    }
}
