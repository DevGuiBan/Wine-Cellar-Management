package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ClientDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

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
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPhone_number(clientDTO.getPhone_number());
        client.setAddress(clientDTO.getAddress());
        return clientRepository.save(client);
    }

    public List<ClientDTO> listAll() {
        return clientRepository.findAll().stream().map(client -> {
            ClientDTO dto = new ClientDTO();
            dto.setName(client.getName());
            dto.setEmail(client.getEmail());
            dto.setPhone_number(client.getPhone_number());
            dto.setAddress(client.getAddress());
            dto.setId(client.getId());
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
                    if (clientDTO.getPhone_number() != null && !clientDTO.getPhone_number().isBlank()) {
                        existingClient.setPhone_number(clientDTO.getPhone_number());
                    }
                    if (clientDTO.getEmail() != null && !clientDTO.getEmail().isBlank()) {
                        existingClient.setEmail(clientDTO.getEmail());
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
