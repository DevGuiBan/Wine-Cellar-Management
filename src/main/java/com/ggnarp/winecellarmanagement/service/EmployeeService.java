package com.ggnarp.winecellarmanagement.service;

import com.ggnarp.winecellarmanagement.dto.EmployeeDTO;
import com.ggnarp.winecellarmanagement.entity.Employee;
import com.ggnarp.winecellarmanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(EmployeeDTO employerDTO) {
        if(this.employeeRepository.existsByEmail(employerDTO.getEmail())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com esse e-mail");
        }
        if(this.employeeRepository.existsByCpf(employerDTO.getCpf())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com esse cpf");
        }
        if(this.employeeRepository.existsByPhoneNumber(employerDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado com esse número de telefone");
        }
        if(employerDTO.getDateBirth().equals(" / / ")){
            throw new IllegalArgumentException("A data de nascimento não pode ser vázia!");
        }

        if (employerDTO.getName().isBlank()){
            throw new IllegalArgumentException("O nome do Funcionário não pode ser vázio");
        }
        if(employerDTO.getEmail().isBlank()){
            throw new IllegalArgumentException("O e-mail do Funcionário não pode ser vázio");
        }

        Employee employee = new Employee();
        employee.setName(employerDTO.getName());
        employee.setEmail(employerDTO.getEmail());
        employee.setPhoneNumber(employerDTO.getPhoneNumber());
        employee.setAddress(employerDTO.getAddress());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(employerDTO.getDateBirth(), formatter);
        employee.setDateBirth(data);
        employee.setCpf(employerDTO.getCpf());
        return employeeRepository.save(employee);
    }

    public List<EmployeeDTO> listAll() {
        return employeeRepository.findAllByOrderByNameAsc().stream().map(client -> {
            EmployeeDTO dto = new EmployeeDTO();
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
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Funcionário com este ID " + id + " não foi encontrado");
        }
        employeeRepository.deleteById(id);
    }

    public Employee update(UUID id, EmployeeDTO employerDTO) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    if (!employerDTO.getName().isBlank()) {
                        existingEmployee.setName(employerDTO.getName());
                    }
                    if (employerDTO.getAddress() != null) {
                        String regex = "^(.+?), (.+?), (\\d+), (.+)-([A-Z]{2})$";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(employerDTO.getAddress());
                        if(matcher.matches()){
                            existingEmployee.setAddress(employerDTO.getAddress());
                        }else{
                            throw new IllegalArgumentException("O Endereço deve ser no formato Rua, Bairro, Número, Cidade-UF");
                        }

                    }
                    if (employerDTO.getPhoneNumber() != null && !employerDTO.getPhoneNumber().isBlank()) {
                        if(!this.employeeRepository.existsByPhoneNumber(employerDTO.getPhoneNumber())) {
                            existingEmployee.setPhoneNumber(employerDTO.getPhoneNumber());
                        }

                    }
                    if (employerDTO.getEmail() != null && !employerDTO.getEmail().isBlank()) {
                        if(!this.employeeRepository.existsByEmail(employerDTO.getEmail())){
                            existingEmployee.setEmail(employerDTO.getEmail());
                        }

                    }
                    if(employerDTO.getDateBirth()!= null && !employerDTO.getDateBirth().isBlank()){
                        if(" / / ".equals(employerDTO.getDateBirth())){
                            throw new IllegalArgumentException("A data não pode ser vázia!");
                        }else {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate data = LocalDate.parse(employerDTO.getDateBirth(), formatter);
                            existingEmployee.setDateBirth(data);
                        }

                    }
                    if(employerDTO.getCpf() != null && !employerDTO.getCpf().isBlank()){
                        if(!employerDTO.getCpf().equals(existingEmployee.getCpf())) {
                            existingEmployee.setCpf(employerDTO.getCpf());
                        }
                    }

                    return employeeRepository.save(existingEmployee);
                })
                .orElseThrow(() -> new ResourceAccessException("Funcionário com o id " + id + " não encontrado"));
    }

    public Employee getById(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário com o id " + id + " não encontrado"));
    }

    public List<EmployeeDTO> listAllByDate(String start_date, String end_date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate date_in = LocalDate.parse(start_date, formatter);
        LocalDate date_out = LocalDate.parse(end_date, formatter);

        return employeeRepository.findAllByDateBirthBetweenOrderByDateBirthAsc(date_in, date_out).stream().map(employee -> {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setName(employee.getName());
            dto.setEmail(employee.getEmail());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setAddress(employee.getAddress());
            dto.setId(employee.getId());

            String dataFormatada = employee.getDateBirth().format(formatter);
            dto.setDateBirth(dataFormatada);
            dto.setCpf(employee.getCpf());

            return dto;
        }).collect(Collectors.toList());
    }

    public List<EmployeeDTO> listAllByAdress(String adress){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return employeeRepository.searchEmployeeByAddress(adress).stream().map(employee -> {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setName(employee.getName());
            dto.setEmail(employee.getEmail());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setAddress(employee.getAddress().replace(":",""));
            dto.setId(employee.getId());
            String dataFormatada = employee.getDateBirth().format(formatter);
            dto.setDateBirth(dataFormatada);
            dto.setCpf(employee.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<EmployeeDTO> listAllByDateAndAddress(String startDate, String endDate, String addressTerm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateIn = LocalDate.parse(startDate, formatter);
        LocalDate dateOut = LocalDate.parse(endDate, formatter);

        List<Employee> employees = employeeRepository.searchEmployeeByDateAndAddress(dateIn, dateOut, addressTerm);

        return employees.stream().map(employee -> {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setName(employee.getName());
            dto.setEmail(employee.getEmail());
            dto.setPhoneNumber(employee.getPhoneNumber());
            dto.setAddress(employee.getAddress());
            dto.setId(employee.getId());
            dto.setDateBirth(employee.getDateBirth().format(formatter));
            dto.setCpf(employee.getCpf());
            return dto;
        }).collect(Collectors.toList());
    }

}
