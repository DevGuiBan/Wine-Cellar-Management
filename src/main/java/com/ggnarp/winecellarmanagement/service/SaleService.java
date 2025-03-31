package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ReportDTO;
import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.entity.Product;
import com.ggnarp.winecellarmanagement.entity.Sale;
import com.ggnarp.winecellarmanagement.entity.SaleProduct;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import com.ggnarp.winecellarmanagement.repository.ProductRepository;
import com.ggnarp.winecellarmanagement.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.criteria.Predicate;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
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

    public SaleDTO convertToDTO(Sale sale) {
        SaleDTO dto = new SaleDTO();
        dto.setId(sale.getId());
        dto.setClientId(sale.getClient().getId());
        dto.setClient(sale.getClient());
        dto.setSaleDate(sale.getSaleDate());
        dto.setTotalPrice(sale.getTotalPrice());
        dto.setDiscount(sale.getDiscount());
        dto.setPaymentMethod(sale.getPaymentMethod());

        List<SaleDTO.SaleProductDTO> productDTOs = sale.getSaleProducts().stream().map(sp -> {
            SaleDTO.SaleProductDTO spDTO = new SaleDTO.SaleProductDTO();
            spDTO.setProductId(sp.getProduct().getId());
            spDTO.setQuantity(sp.getQuantity());
            spDTO.setName(sp.getProduct().getName());
            spDTO.setPrice(sp.getProduct().getPrice());
            return spDTO;
        }).toList();

        dto.setProducts(productDTOs);

        return dto;
    }

    @Transactional(readOnly = true)
    public List<SaleDTO> listSales() {
        return saleRepository.findAll().stream().map(sale -> {
            SaleDTO saleDTO = new SaleDTO();
            saleDTO = convertToDTO(sale);
            return saleDTO;
        }).collect(Collectors.toList());
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

    @Transactional(readOnly = true)
    public ReportDTO generateReports(String dateIn, String dateOut) {
        try {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date_in = LocalDate.parse(dateIn, formatter);
                LocalDate date_out = LocalDate.parse(dateOut, formatter);

                List<SaleDTO> sales = saleRepository.findBySaleDateBetween(date_in, date_out)
                        .stream()
                        .map(this::convertToDTO)
                        .toList();

                if (sales.isEmpty()) {
                    throw new IllegalArgumentException("Não foi possível encontrar vendas nesse recorte de tempo.");
                }

                List<ReportDTO.SaleReportDTO> saleReportList = sales.stream()
                        .flatMap(sale -> sale.getProducts().stream())
                        .collect(Collectors.groupingBy(
                                SaleDTO.SaleProductDTO::getName,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        saleProducts -> {
                                            int totalQuantity = saleProducts.stream().mapToInt(SaleDTO.SaleProductDTO::getQuantity).sum();
                                            BigDecimal totalSum = saleProducts.stream()
                                                    .map(sp -> sp.getPrice().multiply(BigDecimal.valueOf(sp.getQuantity())))
                                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                                            ReportDTO.SaleReportDTO reportDTO = new ReportDTO.SaleReportDTO();
                                            reportDTO.setName(saleProducts.get(0).getName());
                                            reportDTO.setQuantity(totalQuantity);
                                            reportDTO.setTotalSum(totalSum);
                                            return reportDTO;
                                        }
                                )
                        ))
                        .values()
                        .stream()
                        .sorted(Comparator.comparingInt(ReportDTO.SaleReportDTO::getQuantity).reversed())
                        .collect(Collectors.toList());

                BigDecimal totalSalesAmount = sales.stream()
                        .map(SaleDTO::getTotalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                long daysWithSales = sales.stream()
                        .map(SaleDTO::getSaleDate)
                        .distinct()
                        .count();

                BigDecimal avgSale = daysWithSales > 0 ? totalSalesAmount.divide(BigDecimal.valueOf(daysWithSales), BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;

                long totalDays = ChronoUnit.DAYS.between(date_in, date_out) + 1;
                BigDecimal avgSaleTotal = totalSalesAmount.divide(BigDecimal.valueOf(totalDays), BigDecimal.ROUND_HALF_UP);

                ReportDTO reportDTO = new ReportDTO();
                reportDTO.setSales(saleReportList);
                reportDTO.setAvgSale(avgSale);
                reportDTO.setAvgSaleTotal(avgSaleTotal);

                return reportDTO;

            } catch (Exception e) {
                throw new IllegalArgumentException("Insira uma data válida para gerar o relatório!");
            }

        } catch (Exception e) {
            throw new ResourceAccessException("Ocorreu um erro ao pesquisar as vendas.\n" + e.getMessage());
        }
    }

    public List<Sale> searchSales(String clientName, String productName) {

        Specification<Sale> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (clientName != null && !clientName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("client").get("name"), "%" + clientName + "%"));
            }

            if (productName != null && !productName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("product").get("name"), "%" + productName + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return saleRepository.findAll(spec);
    }
}
