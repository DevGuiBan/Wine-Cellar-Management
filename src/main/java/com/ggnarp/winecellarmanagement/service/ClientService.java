package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ClientDTO;
import com.ggnarp.winecellarmanagement.dto.EmployeeDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.entity.Employee;
import com.ggnarp.winecellarmanagement.repository.ClientRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        else if (clientDTO.getName().equals("Nome do cliente")){
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
        if(clientDTO.getDateBirth().equals(" / / ")){
            throw new IllegalArgumentException("A data de nascimento não pode ser vázia!");
        }

        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setAddress(clientDTO.getAddress());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(clientDTO.getDateBirth(), formatter);
        client.setDateBirth(data);
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
            String dataFormatada = client.getDateBirth().format(formatter);
            dto.setDateBirth(dataFormatada);
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
                    if(clientDTO.getName().equals("Nome do cliente")){
                        throw new IllegalArgumentException("O nome do Cliente deve ser inserido da forma correta!");
                    }
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
                        Client cli = clientRepository.findByPhoneNumber(clientDTO.getPhoneNumber());
                        if(this.clientRepository.existsClientByPhoneNumber(clientDTO.getPhoneNumber())) {
                            if(!cli.getId().equals(existingClient.getId())){
                                throw new IllegalArgumentException("Já existe um cliente com este número de telefone.");
                            }
                            existingClient.setPhoneNumber(clientDTO.getPhoneNumber());
                        }

                    }
                    if (clientDTO.getEmail() != null && !clientDTO.getEmail().isBlank()) {
                        Client cli = clientRepository.findByEmail(clientDTO.getEmail());
                        if(this.clientRepository.existsByEmail(clientDTO.getEmail())){
                            if(!cli.getId().equals(existingClient.getId())){
                                throw new IllegalArgumentException("Já existe um cliente com este endereço de e-mail.");
                            }
                            existingClient.setEmail(clientDTO.getEmail());
                        }
                        else if (clientDTO.getEmail().equals("email@gmail.com")){
                            throw new IllegalArgumentException("Insira um endereço de e-mail válido!");
                        }
                        else{
                            String[] parts = clientDTO.getEmail().split("@");
                            boolean result = parts.length == 2 && !parts[1].isEmpty();
                            if(!result){
                                throw new IllegalArgumentException("Insirá um endereço de e-mail válido!");
                            }else{
                                existingClient.setEmail(clientDTO.getEmail());
                            }
                        }

                    }
                    if(clientDTO.getDateBirth()!= null && !clientDTO.getDateBirth().isBlank()){
                        if(" / / ".equals(clientDTO.getDateBirth())){
                            throw new IllegalArgumentException("A data não pode ser vázia!");
                        }else {
                            try{
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDate data = LocalDate.parse(clientDTO.getDateBirth(), formatter);
                                existingClient.setDateBirth(data);
                            } catch (Exception e) {
                                throw new IllegalArgumentException("Insira uma data de Nascimento Válida.");
                            }

                        }

                    }
                    if(clientDTO.getCpf() != null && !clientDTO.getCpf().isBlank()){
                        Client cli = clientRepository.findByCpf(clientDTO.getCpf());
                        if(clientDTO.getCpf().equals(existingClient.getCpf())) {
                            if(!cli.getId().equals(existingClient.getId())){
                                throw new IllegalArgumentException("Já existe um cliente com este CPF.");
                            }
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

    public List<ClientDTO> listAllByDate(String start_date, String end_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date_in = LocalDate.parse(start_date, formatter);
        LocalDate date_out = LocalDate.parse(end_date, formatter);

        return clientRepository.findAllByDateBirthBetweenOrderByDateBirthAsc(date_in,date_out).stream().map(client -> {
            ClientDTO dto = new ClientDTO();
            dto.setName(client.getName());
            dto.setEmail(client.getEmail());
            dto.setPhoneNumber(client.getPhoneNumber());
            dto.setAddress(client.getAddress());
            dto.setId(client.getId());
            String dataFormatada = client.getDateBirth().format(formatter);
            dto.setDateBirth(dataFormatada);
            dto.setCpf(client.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<ClientDTO> listAllByAdress(String adress){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return clientRepository.searchClientByAddress(adress).stream().map(client -> {
            ClientDTO dto = new ClientDTO();
            dto.setName(client.getName());
            dto.setEmail(client.getEmail());
            dto.setPhoneNumber(client.getPhoneNumber());
            dto.setAddress(client.getAddress().replace(":",""));
            dto.setId(client.getId());
            String dataFormatada = client.getDateBirth().format(formatter);
            dto.setDateBirth(dataFormatada);
            dto.setCpf(client.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<ClientDTO> listAllByDateAndAddress(String startDate, String endDate, String addressTerm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateIn = LocalDate.parse(startDate, formatter);
        LocalDate dateOut = LocalDate.parse(endDate, formatter);

        List<Client> clients = clientRepository.searchClientByDateAndAddress(dateIn, dateOut, addressTerm);

        return clients.stream().map(client -> {
            ClientDTO dto = new ClientDTO();
            dto.setName(client.getName());
            dto.setEmail(client.getEmail());
            dto.setPhoneNumber(client.getPhoneNumber());
            dto.setAddress(client.getAddress());
            dto.setId(client.getId());
            dto.setDateBirth(client.getDateBirth().format(formatter));
            dto.setCpf(client.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<Client> searchClient(String name, String email, String cpf) {
        Specification<Client> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(name != null && !name.isBlank()){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            if(email != null && !email.isBlank()){
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));
            }

            if(cpf != null && !cpf.isBlank()){
                predicates.add(criteriaBuilder.equal(root.get("cpf"), cpf));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return clientRepository.findAll(spec);
    }
}
