package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.entity.Sale;
import com.ggnarp.winecellarmanagement.entity.SaleProduct;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import com.ggnarp.winecellarmanagement.repository.ProductRepository;
import com.ggnarp.winecellarmanagement.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

        List<SaleProduct> saleProducts = saleDTO.getProducts().stream().map(dto -> {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new ResourceAccessException("Produto não encontrado"));

            if (product.getQuantity() < dto.getQuantity()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + product.getName());
            }

            // Reduzir estoque
            product.setQuantity(product.getQuantity() - dto.getQuantity());
            productRepository.save(product);

            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setProduct(product);
            saleProduct.setQuantity(dto.getQuantity());
            return saleProduct;
        }).collect(Collectors.toList());

        BigDecimal totalPrice = saleProducts.stream()
                .map(sp -> sp.getProduct().getPrice().multiply(BigDecimal.valueOf(sp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Aplicar desconto se houver (aqui não está sendo por porcentagem está no bruto, apenas subtraindo <3)
        BigDecimal discount = saleDTO.getDiscount() != null ? saleDTO.getDiscount() : BigDecimal.ZERO;

        // Verificar se hoje é o aniversário do cliente e aplicar 10% de desconto
        LocalDate today = LocalDate.now();
        if (client.getDateBirth().getDayOfMonth() == today.getDayOfMonth() &&
                client.getDateBirth().getMonthValue() == today.getMonthValue()) {
            discount = discount.add(totalPrice.multiply(BigDecimal.valueOf(0.10)));
        }

        Sale sale = new Sale();
        sale.setClient(client);
        sale.setSaleProducts(saleProducts);
        sale.setSaleDate(today);
        sale.setTotalPrice(totalPrice.subtract(discount)); // Aplicar desconto no preço final
        sale.setDiscount(discount);
        sale.setPaymentMethod(saleDTO.getPaymentMethod());

        saleProducts.forEach(sp -> sp.setSale(sale));
        return saleRepository.save(sale);
    }

    public List<Sale> listSales() {
        return saleRepository.findAll();
    }

    public Sale updateSale(Long id, SaleDTO saleDTO) {
        Optional<Sale> existingSaleOpt = saleRepository.findById(id);
        if (existingSaleOpt.isEmpty()) {
            throw new ResourceAccessException("Venda com ID " + id + " não encontrada.");
        }

        Sale existingSale = existingSaleOpt.get();

        if (saleDTO.getClientId() != null) {
            Client client = clientRepository.findById(saleDTO.getClientId())
                    .orElseThrow(() -> new ResourceAccessException("Cliente não encontrado"));
            existingSale.setClient(client);
        }

        // Atualizar data da venda, se fornecido
        if (saleDTO.getSaleDate() != null) {
            existingSale.setSaleDate(saleDTO.getSaleDate());
        }

        // Atualizar produtos vendidos, se fornecido
        if (saleDTO.getProducts() != null && !saleDTO.getProducts().isEmpty()) {
            List<SaleProduct> updatedSaleProducts = saleDTO.getProducts().stream().map(dto -> {
                Product product = productRepository.findById(dto.getProductId())
                        .orElseThrow(() -> new ResourceAccessException("Produto não encontrado"));

                if (product.getQuantity() < dto.getQuantity()) {
                    throw new IllegalArgumentException("Estoque insuficiente para o produto: " + product.getName());
                }

                product.setQuantity(product.getQuantity() - dto.getQuantity());
                productRepository.save(product);

                SaleProduct saleProduct = new SaleProduct();
                saleProduct.setProduct(product);
                saleProduct.setQuantity(dto.getQuantity());
                saleProduct.setSale(existingSale);
                return saleProduct;
            }).collect(Collectors.toList());

            existingSale.setSaleProducts(updatedSaleProducts);
        }

        BigDecimal totalPrice = existingSale.getSaleProducts().stream()
                .map(sp -> sp.getProduct().getPrice().multiply(BigDecimal.valueOf(sp.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discount = saleDTO.getDiscount() != null ? saleDTO.getDiscount() : BigDecimal.ZERO;
        existingSale.setTotalPrice(totalPrice.subtract(discount));
        existingSale.setDiscount(discount);

        if (saleDTO.getPaymentMethod() != null) {
            existingSale.setPaymentMethod(saleDTO.getPaymentMethod());
        }

        return saleRepository.save(existingSale);
    }

    public void deleteSale(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new ResourceAccessException("Venda com ID " + id + " não encontrada.");
        }
        saleRepository.deleteById(id);
    }
}
