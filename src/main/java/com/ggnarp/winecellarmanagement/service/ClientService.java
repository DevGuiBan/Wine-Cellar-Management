package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ClientDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
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
}
