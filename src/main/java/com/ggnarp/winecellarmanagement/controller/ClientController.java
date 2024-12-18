package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.ClientDTO;
import com.ggnarp.winecellarmanagement.entity.Client;
import com.ggnarp.winecellarmanagement.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> resgister(@RequestBody @Valid ClientDTO clientDTO) {
        Client client = clientService.save(clientDTO);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> list() {
        List<ClientDTO> clients = clientService.listAll();
        return ResponseEntity.ok(clients);
    }
}
