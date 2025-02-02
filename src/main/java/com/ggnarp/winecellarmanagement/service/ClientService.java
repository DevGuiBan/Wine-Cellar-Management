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
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(ClientDTO clientDTO) {
        if(this.clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse e-mail");
        }
        if(this.clientRepository.existsByCpf(clientDTO.getCpf())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse cpf");
        }
        if(this.clientRepository.existsClientByPhoneNumber(clientDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse número de telefone");
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
                        existingClient.setAddress(clientDTO.getAddress());
                    }
                    if (clientDTO.getPhoneNumber() != null && !clientDTO.getPhoneNumber().isBlank()) {
                        if(!clientDTO.getPhoneNumber().equals(existingClient.getPhoneNumber())) {
                            existingClient.setPhoneNumber(clientDTO.getPhoneNumber());
                        }

                    }
                    if (clientDTO.getEmail() != null && !clientDTO.getEmail().isBlank()) {
                        if(!clientDTO.getEmail().equals(existingClient.getEmail())){
                            existingClient.setEmail(clientDTO.getEmail());
                        }

                    }
                    if(clientDTO.getDate_brith()!= null && !clientDTO.getDate_brith().isBlank()){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate data = LocalDate.parse(clientDTO.getDate_brith(), formatter);
                        existingClient.setDate_brith(data);
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
