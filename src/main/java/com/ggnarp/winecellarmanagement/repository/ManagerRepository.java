package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Manager;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    Manager findByEmailAndPassword(String email, String password);

    Manager findByCpf(@NotNull(message = "O CPF não pode ser um valor nulo") @NotBlank(message = "O CPF não pode ser vázio!") @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",message = "O CPF desse ser no formato 000.000.000-00!") String cpf);

    Manager findByEmail(@Email String email);
}
