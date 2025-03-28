package com.ggnarp.winecellarmanagement.repository;

import com.ggnarp.winecellarmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByCpf(String cpf);
    List<Employee> findAllByOrderByNameAsc();
    List<Employee> findAllByDateBirthBetweenOrderByDateBirthAsc(LocalDate startDate, LocalDate endDate);

    @Query("SELECT f FROM Employee f WHERE " +
            "LOWER(REPLACE(f.address, ':-', '-')) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Employee> searchEmployeeByAddress(@Param("termo") String termo);

    @Query("SELECT f FROM Employee f WHERE " +
            "f.dateBirth BETWEEN :startDate AND :endDate " +
            "AND LOWER(REPLACE(f.address, ':-', '-')) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Employee> searchEmployeeByDateAndAddress(@Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  @Param("termo") String termo);

    boolean existsByEmailAndPassword(String email, String password);

    Employee findByPhoneNumber(String phoneNumber);

    Employee findByEmail(String email);

    Employee findByCpf(String cpf);
}
