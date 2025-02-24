package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.ClientDTO;
import com.ggnarp.winecellarmanagement.dto.DateRangeDTO;
import com.ggnarp.winecellarmanagement.dto.EmployeeDTO;
import com.ggnarp.winecellarmanagement.entity.Employee;
import com.ggnarp.winecellarmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> resgister(@RequestBody @Valid EmployeeDTO employeerDTO) {
        try{
            Employee employee = employeeService.save(employeerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(employee);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao cadastar o funcionário no servidor");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    @GetMapping("/date")
    public ResponseEntity<?> date(@RequestParam("start_date") String startDate,
                                  @RequestParam("end_date") String endDate) {
        try {
            List<EmployeeDTO> employees = employeeService.listAllByDate(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar os Funcionários no servidor entre essas datas");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/searchByAeD")
    public ResponseEntity<?> searchEmployeeByDateAndAddress(@RequestParam("start_date") String startDate,
                                                            @RequestParam("end_date") String endDate,
                                                            @RequestParam("addressTerm") String addressTerm) {
        try {
            List<EmployeeDTO> employees = employeeService.listAllByDateAndAddress(startDate, endDate, addressTerm);
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar os Funcionários com os critérios fornecidos");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }


    @GetMapping("/address/{address}")
    public ResponseEntity<?> address(@PathVariable String address) {
        try {
            List<EmployeeDTO> employees = employeeService.listAllByAdress(address);
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        }catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar os Funcionários no servidor com estes endereços!");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<?> list() {
        try{
            List<EmployeeDTO> employees = employeeService.listAll();
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar os funcionários no servidor");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplier(@PathVariable UUID id) {
        try{
            Employee employee = employeeService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar um funcionário com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid EmployeeDTO employeerDTO) {
        try{
            Employee updatedCLient = employeeService.update(id, employeerDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCLient);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao atualizar as informações do funcionário com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try{
            this.employeeService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao excluir o funcionário com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
