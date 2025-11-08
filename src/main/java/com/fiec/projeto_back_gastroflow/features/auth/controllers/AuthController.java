package com.fiec.projeto_back_gastroflow.features.auth.controllers;

import com.fiec.projeto_back_gastroflow.features.auth.dto.LoginRequest;
import com.fiec.projeto_back_gastroflow.features.auth.dto.LoginResponse;
import com.fiec.projeto_back_gastroflow.features.auth.dto.RegisterRequest;
import com.fiec.projeto_back_gastroflow.features.auth.services.AuthService;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import com.fiec.projeto_back_gastroflow.utils.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// IMPORTES SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Slf4j
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários.")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
// ... (omitting constructor)

    @Operation(summary = "Registra um novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso.", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Dados de registro inválidos.")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User newUser = authService.register(request);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Realiza login", description = "Autentica o usuário e retorna um JWT para acesso aos endpoints protegidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido.", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("AuthController - login - usuario tentou logar com {}", request);
        User loggedInUser = authService.login(request);
        String jwtToken = this.jwtService.generateToken(loggedInUser);
        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}