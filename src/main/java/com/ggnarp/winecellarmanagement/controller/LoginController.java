package com.ggnarp.winecellarmanagement.controller;

import com.ggnarp.winecellarmanagement.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ggnarp.winecellarmanagement.service.LoginService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean authenticated = loginService.Login(loginRequest);

        if (authenticated) {
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "Login realizado com sucesso!");
            successResponse.put("authenticated", true);
            return ResponseEntity.ok(successResponse);
        }

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Credenciais inválidas!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
