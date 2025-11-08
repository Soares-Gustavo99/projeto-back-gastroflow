package com.fiec.projeto_back_gastroflow.features.admin.controllers;

import com.fiec.projeto_back_gastroflow.features.user.models.User;
// IMPORTES SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Administração", description = "Endpoints para funcionalidades exclusivas de Administradores.")
@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    @Operation(summary = "Teste de acesso para Administradores", description = "Endpoint de teste que requer a permissão 'ROLE_ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acesso autorizado e teste executado com sucesso."),
            @ApiResponse(responseCode = "403", description = "Acesso negado. O usuário não possui a permissão 'ROLE_ADMIN'.")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/test")
    public void testRequest(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        System.out.println(user);

    }
}