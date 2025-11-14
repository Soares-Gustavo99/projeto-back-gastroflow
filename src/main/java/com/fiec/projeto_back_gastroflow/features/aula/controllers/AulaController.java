package com.fiec.projeto_back_gastroflow.features.aula.controllers;

import com.fiec.projeto_back_gastroflow.features.aula.dto.AulaDTO;
import com.fiec.projeto_back_gastroflow.features.aula.services.impl.AulaServiceImpl;
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
import java.util.UUID;


@Tag(name = "Aulas", description = "Gerenciamento de Aulas.")
@RestController
@RequestMapping("/v1/api/aulas")
@AllArgsConstructor
public class AulaController {

    private final AulaServiceImpl aulaService;

    // Criar uma aula
    @Operation(summary = "Cria uma nova aula", description = "Permitido para usuários com 'ADMIN' ou 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aula cadastrada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados da aula inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    public ResponseEntity<String> createAula(
            @AuthenticationPrincipal User userLogado,
            @Parameter(description = "Dados da aula a ser criada.") @RequestBody AulaDTO aulaDTO) {
        UUID usuarioId = userLogado.getId();
        aulaService.createAula(aulaDTO, usuarioId);
        return ResponseEntity.ok("Aula cadastrada com sucesso!");
    }

    // Buscar aula por ID

    @Operation(summary = "Busca uma aula por ID", description = "Permitido para usuários com 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aula encontrada."),
            @ApiResponse(responseCode = "404", description = "Aula não encontrada.")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<AulaDTO> getById(@Parameter(description = "ID da aula.") @PathVariable Long id) {
        AulaDTO aula = aulaService.getById(id);
        if (aula == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aula);
    }

    // Listar todas as aulas
    @Operation(summary = "Lista todas as aulas", description = "Permitido para usuários com 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de aulas retornada com sucesso.")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<List<AulaDTO>> findAll() {
        return ResponseEntity.ok(aulaService.findAll());
    }

    // Atualizar aula
    @Operation(summary = "Atualiza uma aula existente por ID", description = "Permitido apenas para usuários com 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aula atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Aula não encontrada."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateAulaById(
            @AuthenticationPrincipal User userLogado,
            @Parameter(description = "ID da aula a ser atualizada.") @PathVariable Long id,
            @Parameter(description = "Novos dados da aula.") @RequestBody AulaDTO aulaDTO) {

        UUID usuarioId = userLogado.getId();

        boolean updated = aulaService.updateAulaById(id, aulaDTO, usuarioId);

        if (updated) {
            return ResponseEntity.ok("Aula atualizada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar aula
    @Operation(summary = "Deleta uma aula por ID", description = "Permitido apenas para usuários com 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aula excluída com sucesso."),
            @ApiResponse(responseCode = "404", description = "Aula não encontrada."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAulaById(@Parameter(description = "ID da aula a ser deletada.") @PathVariable Long id) {
        // ... (Corpo do método omitido)
        return null; // Apenas para compilar
    }
}