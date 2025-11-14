package com.fiec.projeto_back_gastroflow.features.receita.controllers;

import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;
import com.fiec.projeto_back_gastroflow.features.receita.services.impl.ReceitaServiceImpl;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Receitas", description = "Gerenciamento de receitas e ficha técnica.")
@RestController
@RequestMapping("/v1/api/receitas")
@AllArgsConstructor
public class ReceitaController {

    private final ReceitaServiceImpl receitaService;

    // Criar uma receita
    @Operation(summary = "Cria uma nova receita", description = "Permitido para usuários com 'ADMIN' ou 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita cadastrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados da receita inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    public ResponseEntity<String> createReceita(
            @AuthenticationPrincipal User userLogado,
            @Parameter(description = "Dados da receita a ser criada.") @RequestBody ReceitaDTO receitaDTO) {        java.util.UUID usuarioId = userLogado.getId();
        receitaService.createReceita(receitaDTO, usuarioId);
        return ResponseEntity.ok("Receita cadastrada com sucesso!");
    }

    // Buscar receita por ID
    @Operation(summary = "Busca uma receita por ID", description = "Permitido para usuários com 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita encontrada."),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada.")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<ReceitaDTO> getById(@Parameter(description = "ID da receita.") @PathVariable Long id) {
        ReceitaDTO receita = receitaService.getById(id);
        if (receita == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receita);
    }

    // Listar todas as receitas
    @Operation(summary = "Lista todas as receitas", description = "Permitido para usuários com 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas retornada com sucesso.")
    })
    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<List<ReceitaDTO>> findAll() {
        return ResponseEntity.ok(receitaService.findAll());
    }

    // Atualizar receita
    @Operation(summary = "Atualiza uma receita existente por ID", description = "Permitido apenas para usuários com 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateReceitaById(
            @AuthenticationPrincipal User userLogado,
            @Parameter(description = "ID da receita a ser atualizada.") @PathVariable Long id,
            @Parameter(description = "Novos dados da receita.") @RequestBody ReceitaDTO receitaDTO) {

        java.util.UUID usuarioId = userLogado.getId();

        boolean updated = receitaService.updateReceitaById(id, receitaDTO, usuarioId);

        if (updated) {
            return ResponseEntity.ok("Receita atualizada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar receita
    @Operation(summary = "Deleta uma receita por ID", description = "Permitido apenas para usuários com 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita excluída com sucesso."),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteReceitaById(@Parameter(description = "ID da receita a ser deletada.") @PathVariable Long id) {
        receitaService.deleteReceitaById(id);
        return ResponseEntity.ok("Receita deletada com sucesso!");
    }
}
