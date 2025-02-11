package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.SaleDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.entity.Sale;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import com.ggnarp.winecellarmanagement.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;

    public SaleService(SaleRepository saleRepository , ClientRepository clientRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
    }

    public Sale save(SaleDTO saleDTO) {

        Client client = clientRepository.findById(saleDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + saleDTO.getClientId() + " não encontrado"));

        Sale sale = new Sale();
        sale.setClient(client);
        sale.setTotalValue(saleDTO.getTotalValue());
        sale.setDiscount(saleDTO.getDiscount());
        sale.setPaymentMethod(saleDTO.getPaymentMethod());

        return saleRepository.save(sale);
    }

    public List<SaleDTO> listAll() {
        return saleRepository.findAll().stream().map(sale -> {
            SaleDTO dto = new SaleDTO();
            dto.setId(sale.getId());
            dto.setClientId(sale.getClient().getId());
            dto.setTotalValue(sale.getTotalValue());
            dto.setDiscount(sale.getDiscount());
            dto.setPaymentMethod(sale.getPaymentMethod());
            return dto;
        }).collect(Collectors.toList());
    }

    public void delete(UUID id) {
        if (!saleRepository.existsById(id)) {
            throw new RuntimeException("Sale with ID " + id + " not found");
        }
        saleRepository.deleteById(id);
    }

    public Sale update(UUID id, SaleDTO saleDTO) {

        Client client = clientRepository.findById(saleDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + saleDTO.getClientId() + " não encontrado"));

        return saleRepository.findById(id)
                .map(existingSale -> {
                    if (saleDTO.getClientId() != null) {
                        existingSale.setClient(client);
                    }
                    if (saleDTO.getTotalValue() != null) {
                        existingSale.setTotalValue(saleDTO.getTotalValue());
                    }
                    if (saleDTO.getDiscount() != null) {
                        existingSale.setDiscount(saleDTO.getDiscount());
                    }
                    if (saleDTO.getPaymentMethod() != null) {
                        existingSale.setPaymentMethod(saleDTO.getPaymentMethod());
                    }
                    return saleRepository.save(existingSale);
                })
                .orElseThrow(() -> new ResourceAccessException("Sale with ID " + id + " not found"));
    }

    public Sale getById(UUID id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale with ID " + id + " not found"));
    }
}
