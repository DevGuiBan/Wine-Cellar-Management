package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.ManagerDTO;
import com.ggnarp.winecellarmanagement.entity.Manager;
import com.ggnarp.winecellarmanagement.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public Manager save(ManagerDTO managerDTO) {
        // Verifica se já existe um Manager cadastrado
        if (managerRepository.count() > 0) {
            throw new IllegalArgumentException("Já existe um gerente cadastrado no sistema.");
        }

        if (managerRepository.existsByEmail(managerDTO.getEmail())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com esse e-mail.");
        }
        if (managerRepository.existsByCpf(managerDTO.getCpf())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com esse CPF.");
        }

        if (managerDTO.getName().isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário não pode ser vazio.");
        }
        if (managerDTO.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail do funcionário não pode ser vazio.");
        }
        if (managerDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("A senha do funcionário não pode ser vazia.");
        }

        Manager manager = new Manager();
        manager.setName(managerDTO.getName());
        manager.setEmail(managerDTO.getEmail());
        manager.setCpf(managerDTO.getCpf());
        manager.setPassword(managerDTO.getPassword());

        return managerRepository.save(manager);
    }

    public List<Manager> listAll() {
        return managerRepository.findAll();
    }

    public Manager getById(UUID id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gerente não encontrado com o ID: " + id));
    }

    public Manager update(UUID id, ManagerDTO managerDTO) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gerente não encontrado com o ID: " + id));

        if (!managerDTO.getName().isBlank()) {
            manager.setName(managerDTO.getName());
        }
        if (!managerDTO.getEmail().isBlank()) {
            Manager man = managerRepository.findByEmail(managerDTO.getEmail());
            if (managerRepository.existsByEmail(managerDTO.getEmail())) {
                if (!man.getId().equals(manager.getId())) {
                    throw new IllegalArgumentException("Já existe um Gerente cadastrado com esse e-mail.");
                }
                manager.setEmail(managerDTO.getEmail());
            }
        }
        if (!managerDTO.getCpf().isBlank()) {
            Manager man = managerRepository.findByCpf(managerDTO.getCpf());
            if (managerRepository.existsByCpf(managerDTO.getCpf())) {
                if (!man.getId().equals(manager.getId())) {
                    throw new IllegalArgumentException("Já existe um Gerente cadastrado com esse CPF.");
                }
                manager.setCpf(managerDTO.getCpf());
            }
        }
        if (!managerDTO.getPassword().isBlank()) {
            manager.setPassword(managerDTO.getPassword());
        }

        return managerRepository.save(manager);
    }

    public void delete(UUID id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gerente não encontrado com o ID: " + id));

        managerRepository.delete(manager);
    }

}
