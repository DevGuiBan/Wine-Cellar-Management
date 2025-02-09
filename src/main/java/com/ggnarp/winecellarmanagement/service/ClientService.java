package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ClientDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(ClientDTO clientDTO) {
        if(clientDTO.getName().isBlank()){
            throw new IllegalArgumentException("O nome do cliente não pode ser vázio!");
        }

        if(this.clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse e-mail");
        }
        else if (clientDTO.getEmail().equals("email@gmail.com")){
            throw new IllegalArgumentException("Insirá um endereço de e-mail válido!");
        }
        else{
            String[] parts = clientDTO.getEmail().split("@");
            boolean result = parts.length == 2 && !parts[1].isEmpty();
            if(!result){
                throw new IllegalArgumentException("Insirá um endereço de e-mail válido!");
            }
        }

        if(this.clientRepository.existsByCpf(clientDTO.getCpf())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse cpf");
        }
        if(this.clientRepository.existsClientByPhoneNumber(clientDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse número de telefone");
        }
        if(clientDTO.getDate_brith().equals(" / / ")){
            throw new IllegalArgumentException("A data de nascimento não pode ser vázia!");
        }

        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setAddress(clientDTO.getAddress());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(clientDTO.getDate_brith(), formatter);
        client.setDate_brith(data);
        client.setCpf(clientDTO.getCpf());
        return clientRepository.save(client);
    }

    public List<ClientDTO> listAll() {
        return clientRepository.findAllByOrderByNameAsc().stream().map(client -> {
            ClientDTO dto = new ClientDTO();
            dto.setName(client.getName());
            dto.setEmail(client.getEmail());
            dto.setPhoneNumber(client.getPhoneNumber());
            dto.setAddress(client.getAddress());
            dto.setId(client.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = client.getDate_brith().format(formatter);
            dto.setDate_brith(dataFormatada);
            dto.setCpf(client.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

    public void delete(UUID id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client com este ID " + id + " não foi encontrado");
        }
        clientRepository.deleteById(id);
    }

    public Client update(UUID id, ClientDTO clientDTO) {
        return clientRepository.findById(id)
                .map(existingClient -> {
                    if (!clientDTO.getName().isBlank()) {
                        existingClient.setName(clientDTO.getName());
                    }
                    if (clientDTO.getAddress() != null) {
                        String regex = "^(.+?), (.+?), (\\d+), (.+)-([A-Z]{2})$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(clientDTO.getAddress());
                        if(matcher.matches()){
                            existingClient.setAddress(clientDTO.getAddress());
                        }else{
                            throw new IllegalArgumentException("O Endereço deve ser no formato Rua, Bairro, Número, Cidade-UF");
                        }

                    }
                    if (clientDTO.getPhoneNumber() != null && !clientDTO.getPhoneNumber().isBlank()) {
                        if(!this.clientRepository.existsClientByPhoneNumber(clientDTO.getPhoneNumber())) {
                            existingClient.setPhoneNumber(clientDTO.getPhoneNumber());
                        }

                    }
                    if (clientDTO.getEmail() != null && !clientDTO.getEmail().isBlank()) {
                        if(!this.clientRepository.existsByEmail(clientDTO.getEmail())){
                            existingClient.setEmail(clientDTO.getEmail());
                        }
                        else if (clientDTO.getEmail().equals("email@gmail.com")){
                            throw new IllegalArgumentException("Insirá um endereço de e-mail válido!");
                        }
                        else{
                            String[] parts = clientDTO.getEmail().split("@");
                            boolean result = parts.length == 2 && !parts[1].isEmpty();
                            if(!result){
                                throw new IllegalArgumentException("Insirá um endereço de e-mail válido!");
                            }
                        }

                    }
                    if(clientDTO.getDate_brith()!= null && !clientDTO.getDate_brith().isBlank()){
                        if(" / / ".equals(clientDTO.getDate_brith())){
                            throw new IllegalArgumentException("A data não pode ser vázia!");
                        }else {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate data = LocalDate.parse(clientDTO.getDate_brith(), formatter);
                            existingClient.setDate_brith(data);
                        }

                    }
                    if(clientDTO.getCpf() != null && !clientDTO.getCpf().isBlank()){
                        if(!clientDTO.getCpf().equals(existingClient.getCpf())) {
                            existingClient.setCpf(clientDTO.getCpf());
                        }
                    }

                    return clientRepository.save(existingClient);
                })
                .orElseThrow(() -> new ResourceAccessException("Cliente com o id " + id + " não encontrado"));
    }

    public Client getById(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente com o id " + id + " não encontrado"));
    }
}
