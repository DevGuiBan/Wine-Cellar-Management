package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.ClientDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.service.ClientService;
import com.ggnarp.winecellarmanagement.dto.DateRangeDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> resgister(@RequestBody @Valid ClientDTO clientDTO) {
        try{
            Client client = clientService.save(clientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(client);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao cadastar o cliente no servidor");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

    }

    @GetMapping
    public ResponseEntity<?> list() {
        try{
            List<ClientDTO> clients = clientService.listAll();
            return ResponseEntity.status(HttpStatus.OK).body(clients);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar os clientes no servidor");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @GetMapping("/date")
    public ResponseEntity<?> date(@RequestBody DateRangeDTO rangeDTO) {
        try {
            List<ClientDTO> clientes = clientService.listAllByDate(rangeDTO.getStart_date(), rangeDTO.getEnd_date());
            return ResponseEntity.status(HttpStatus.OK).body(clientes);
        }catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar os clientes no servidor entre essas datas");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable UUID id) {
        try{
            Client client = clientService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(client);
        }
        catch(Exception e){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao procurar um cliente com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid ClientDTO clientDTO) {
        try{
            Client updatedCLient = clientService.update(id, clientDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCLient);
        }
        catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao atualizar as informações do cliente com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        try{
            this.clientService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erro ao excluir o cliente com este id: " + id);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
