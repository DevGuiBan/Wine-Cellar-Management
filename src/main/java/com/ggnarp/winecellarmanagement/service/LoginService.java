package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.LoginRequest;
import com.ggnarp.winecellarmanagement.entity.Employee;
import com.ggnarp.winecellarmanagement.entity.Manager;
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

    public Object Login(LoginRequest loginRequest) {
        try{
            Employee emp =employeeRepository.findByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
            if(emp == null){
                return managerRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
            }else{
                return emp;
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Senha ou E-mail inválidos!");
        }
    }
}
