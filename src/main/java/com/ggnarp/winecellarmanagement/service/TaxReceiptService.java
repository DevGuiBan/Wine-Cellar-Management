package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.entity.Sale;
import org.springframework.stereotype.Service;

import com.ggnarp.winecellarmanagement.entity.TaxReceipt;
import com.ggnarp.winecellarmanagement.dto.TaxReceiptDTO;
import com.ggnarp.winecellarmanagement.repository.TaxReceiptRepository;
import com.ggnarp.winecellarmanagement.repository.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TaxReceiptService {
    private final TaxReceiptRepository taxReceiptRepository;
    private final SaleRepository saleRepository;
    private final SaleService saleService;

    public TaxReceiptService(TaxReceiptRepository taxReceiptRepository, SaleRepository saleRepository, SaleService saleService) {
        this.taxReceiptRepository = taxReceiptRepository;
        this.saleRepository = saleRepository;
        this.saleService = saleService;
    }

    public List<TaxReceiptDTO> listAll() throws Exception {
        try {
            return taxReceiptRepository.findAll().stream().map(TR -> {
                TaxReceiptDTO dto = new TaxReceiptDTO();
                dto.setId(TR.getId());
                dto.setIdSale(TR.getIdSale());
                dto.setQrCode(TR.getQrCode());
                dto.setEnterpriseName("CASA SAN'GIOVANNI LTDA");
                dto.setAddress("RUA 9 N° 1437 BAIRRO DOS SABORES");
                dto.setCityState("Cedro-CE");
                dto.setCnpj("45.987.654/0001-23");
                LocalDate dataAtual = LocalDate.now();
                DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataFormatada = dataAtual.format(formatoData);
                dto.setDateOpenCnpj(dataFormatada);
                dto.setDateOpenCnpj(dataFormatada);
                dto.setIE("25.678.901-5");
                LocalTime horaAtual = LocalTime.now();
                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
                String horaFormatada = horaAtual.format(formatoHora);
                dto.setHourIE(horaFormatada);
                dto.setIM("12345678");
                dto.setCCF("120289");
                dto.setCDD("124857");
                dto.setTax(0.2);
                Optional<Sale> saleOpt = saleRepository.findByIdWithProducts(TR.getIdSale());
                saleOpt.ifPresent(sale -> dto.setSale(saleService.convertToDTO(sale)));
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro ao listar todos os cupons fiscais!\n" + e.getMessage());
        }
    }

    public TaxReceiptDTO getBySaleId(Long saleId) throws Exception {
        try {
            TaxReceipt TR = taxReceiptRepository.findByIdSale(saleId);
            TaxReceiptDTO dto = new TaxReceiptDTO();
            dto.setId(TR.getId());
            dto.setIdSale(TR.getIdSale());
            dto.setQrCode(TR.getQrCode());
            dto.setEnterpriseName("CASA SAN'GIOVANNI LTDA");
            dto.setAddress("RUA 9 N° 1437 BAIRRO DOS SABORES");
            dto.setCityState("Cedro-CE");
            dto.setCnpj("45.987.654/0001-23");
            LocalDate dataAtual = LocalDate.now();
            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = dataAtual.format(formatoData);
            dto.setDateOpenCnpj(dataFormatada);
            dto.setIE("25.678.901-5");
            LocalTime horaAtual = LocalTime.now();
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaFormatada = horaAtual.format(formatoHora);
            dto.setHourIE(horaFormatada);
            dto.setIM("12345678");
            dto.setCCF("120289");
            dto.setCDD("124857");
            dto.setTax(0.2);
            Optional<Sale> saleOpt = saleRepository.findByIdWithProducts(TR.getIdSale());
            saleOpt.ifPresent(sale -> dto.setSale(saleService.convertToDTO(sale)));
            return dto;

        } catch (Exception e) {
            throw new Exception("Ocorreu um erro ao buscar o cupom fiscal!\n" + e.getMessage());
        }
    }

    public TaxReceipt save(Long saleId) throws Exception {
        try {
            TaxReceipt taxReceipt = new TaxReceipt();
            taxReceipt.setIdSale(saleId);
            taxReceipt.setQrCode(generateQrCode());
            return taxReceiptRepository.save(taxReceipt);
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro ao gerar o cupom fiscal!\n" + e.getMessage());
        }
    }

    private String generateQrCode() {
        SecureRandom secureRandom = new SecureRandom();

        return IntStream.range(0, 10)
                .mapToObj(i -> String.format("%04d", secureRandom.nextInt(10000))) // Garante 4 dígitos
                .collect(Collectors.joining("."));
    }
}
