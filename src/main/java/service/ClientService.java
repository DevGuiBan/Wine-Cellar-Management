package service;

import dto.ClientDTO;
import entity.Client;
import repository.ClientRepository;
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
        client.setNome(clientDTO.getNome());
        client.setEmail(clientDTO.getEmail());
        client.setTelefone(clientDTO.getTelefone());
        client.setEndereco(clientDTO.getEndereco());
        return clientRepository.save(client);
    }

    public List<ClientDTO> listAll() {
        return clientRepository.findAll().stream().map(client -> {
            ClientDTO dto = new ClientDTO();
            dto.setNome(client.getNome());
            dto.setEmail(client.getEmail());
            dto.setTelefone(client.getTelefone());
            dto.setEndereco(client.getEndereco());
            return dto;
        }).collect(Collectors.toList());
    }
}
