package com.ggnarp.winecellarmanagement.service;
import org.springframework.stereotype.Service;

import com.ggnarp.winecellarmanagement.entity.TaxReceipt;
import com.ggnarp.winecellarmanagement.dto.TaxReceiptDTO;
import com.ggnarp.winecellarmanagement.repository.TaxReceiptRepository;
import com.ggnarp.winecellarmanagement.repository.SaleRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TaxReceiptService {
    private final TaxReceiptRepository taxReceiptRepository;
    private final SaleRepository saleRepository;

    public TaxReceiptService(TaxReceiptRepository taxReceiptRepository, SaleRepository saleRepository) {
        this.taxReceiptRepository = taxReceiptRepository;
        this.saleRepository = saleRepository;
    }

    public List<TaxReceiptDTO> listAll() throws Exception {
        try{
            return taxReceiptRepository.findAll().stream().map(TR ->{
                TaxReceiptDTO dto = new TaxReceiptDTO();
                dto.setId(TR.getId());
                dto.setIdSale(TR.getIdSale());
                dto.setQrCode(TR.getQrCode());
                dto.setEnterpiseName("CASA SAN'GIOVANNI LTDA");
                dto.setAddress("RUA 9 N° 1437 BAIRRO DOS SABORES");
                dto.setCityState("Cedro-CE");
                dto.setCpnj("45.987.654/0001-23");
                dto.setDateOpenCnpj("23/12/2024");
                dto.setIE("25.678.901-5");
                dto.setHourIE("14:31:20");
                dto.setIM("12345678");
                dto.setCCF("120289");
                dto.setCDD("124857");
                dto.setTax(0.2);
                dto.setSale(saleRepository.findById(TR.getIdSale()));
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro ao listar todos os cupons fiscais!\n"+e.getMessage());
        }
    }

    public List<TaxReceiptDTO> getBySaleId(Long saleId) throws Exception {
        try{
            return taxReceiptRepository.findByIdSale(saleId).stream().map(TR ->{
                TaxReceiptDTO dto = new TaxReceiptDTO();
                dto.setId(TR.getId());
                dto.setIdSale(TR.getIdSale());
                dto.setQrCode(TR.getQrCode());
                dto.setEnterpiseName("CASA SAN'GIOVANNI LTDA");
                dto.setAddress("RUA 9 N° 1437 BAIRRO DOS SABORES");
                dto.setCityState("Cedro-CE");
                dto.setCpnj("45.987.654/0001-23");
                dto.setDateOpenCnpj("23/12/2024");
                dto.setIE("25.678.901-5");
                dto.setHourIE("14:31:20");
                dto.setIM("12345678");
                dto.setCCF("120289");
                dto.setCDD("124857");
                dto.setTax(0.2);
                dto.setSale(saleRepository.findById(TR.getIdSale()));
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro ao buscar o cupom fiscal!\n"+e.getMessage());
        }
    }

    public TaxReceipt save(Long saleId) throws Exception {
        try{
            TaxReceipt taxReceipt = new TaxReceipt();
            taxReceipt.setIdSale(saleId);
            taxReceipt.setQrCode(generateQrCode());
            return taxReceiptRepository.save(taxReceipt);
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro ao gerar o cupom fiscal!\n"+e.getMessage());
        }
    }

    private String generateQrCode(){
        SecureRandom secureRandom = new SecureRandom();

        return IntStream.range(0, 10)
                .mapToObj(i -> String.format("%04d", secureRandom.nextInt(10000))) // Garante 4 dígitos
                .collect(Collectors.joining("."));
    }
}
