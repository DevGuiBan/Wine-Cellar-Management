package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Client;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsClientByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByCpf(String email);

    List<Client> findAllByOrderByNameAsc();
}
