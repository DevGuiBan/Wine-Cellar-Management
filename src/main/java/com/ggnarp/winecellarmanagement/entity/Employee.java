package com.ggnarp.winecellarmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,name = "date_birth")
    private LocalDate dateBirth;

    @Column(nullable = false,unique = true,length = 14)
    private String cpf;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = false,length = 15,name = "phone_number")
    private String phoneNumber;
}
