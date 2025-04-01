package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID>, JpaSpecificationExecutor<Client> {
    boolean existsClientByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByCpf(String email);

    List<Client> findAllByOrderByNameAsc();

    List<Client> findAllByDateBirthBetweenOrderByDateBirthAsc(LocalDate startDate, LocalDate endDate);

    @Query("SELECT f FROM Client f WHERE " +
            "LOWER(REPLACE(f.address, ':-', '-')) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Client> searchClientByAddress(@Param("termo") String termo);

    @Query("SELECT f FROM Client f WHERE " +
            "f.dateBirth BETWEEN :startDate AND :endDate " +
            "AND LOWER(REPLACE(f.address, ':-', '-')) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Client> searchClientByDateAndAddress(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("termo") String termo);

    Client findByPhoneNumber(String phoneNumber);

    Client findByEmail(String email);

    Client findByCpf(String cpf);
}
