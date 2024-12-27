package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
