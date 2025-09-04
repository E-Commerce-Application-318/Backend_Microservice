package com.backend.ddd.controller.resource;

import com.backend.ddd.application.model.AuthResponse;
import com.backend.ddd.application.model.LoginRequest;
import com.backend.ddd.application.model.RegisterRequest;
import com.backend.ddd.application.service.AuthAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.AuthResponseDTO;
import com.backend.ddd.controller.model.dto.LoginRequestDTO;
import com.backend.ddd.controller.model.dto.RegisterRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthAppService authAppService;

    @GetMapping("/index")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> login(
            @RequestBody
            LoginRequestDTO loginRequestDTO
    ){
        LoginRequest loginRequest = new LoginRequest()
                .setUsername(loginRequestDTO.getUsername())
                .setPassword(loginRequestDTO.getPassword());

        AuthResponse authResponse = authAppService.login(loginRequest);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO()
                .setUsername(authResponse.getUsername())
                .setRole(authResponse.getRole());

        if (!authResponse.isSuccess()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(authResponse.getMessage()));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success(authResponse.getMessage(), authResponseDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<AuthResponseDTO>> register(
            @RequestBody
            RegisterRequestDTO registerRequestDTO
    ){
        RegisterRequest registerRequest = new RegisterRequest()
                .setUsername(registerRequestDTO.getUsername())
                .setPassword(registerRequestDTO.getPassword())
                .setConfirmedPassword(registerRequestDTO.getConfirmedPassword())
                .setName(registerRequestDTO.getName())
                .setEmail(registerRequestDTO.getEmail())
                .setPhoneNumber(registerRequestDTO.getPhoneNumber())
                .setRole(registerRequestDTO.getRole())
                .setBirthDate(registerRequestDTO.getBirthDate());

        AuthResponse authResponse = authAppService.register(registerRequest);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO()
                .setUsername(authResponse.getUsername())
                .setRole(authResponse.getRole());

        if (!authResponse.isSuccess()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error(authResponse.getMessage()));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success(authResponse.getMessage(), authResponseDTO));
    }

}
