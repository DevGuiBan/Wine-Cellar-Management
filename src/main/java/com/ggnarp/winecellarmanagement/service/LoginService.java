package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.LoginRequest;
import com.ggnarp.winecellarmanagement.repository.EmployeeRepository;
import com.ggnarp.winecellarmanagement.repository.ManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final ManagerRepository managerRepository;
    private final EmployeeRepository employeeRepository;

    public LoginService(ManagerRepository managerRepository, EmployeeRepository employeeRepository) {
        this.managerRepository = managerRepository;
        this.employeeRepository = employeeRepository;
    }

    public Boolean Login(LoginRequest loginRequest) {
        try{
            return employeeRepository.existsByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword()) || managerRepository.existsByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        } catch (Exception e) {
            throw new IllegalArgumentException("Senha ou E-mail inválidos!");
        }
    }
}
