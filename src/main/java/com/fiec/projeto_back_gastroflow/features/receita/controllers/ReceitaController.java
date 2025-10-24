package com.fiec.projeto_back_gastroflow.features.receita.controllers;

import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;
import com.fiec.projeto_back_gastroflow.features.receita.services.impl.ReceitaServiceImpl;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/receitas")
@AllArgsConstructor
public class ReceitaController {

    private final ReceitaServiceImpl receitaService;

    // Criar uma receita
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    public ResponseEntity<String> createReceita( @AuthenticationPrincipal User userLogado, @RequestBody ReceitaDTO receitaDTO) {
        java.util.UUID usuarioId = userLogado.getId();
        receitaService.createReceita(receitaDTO, usuarioId);
        return ResponseEntity.ok("Receita cadastrada com sucesso!");
    }

    // Buscar receita por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<ReceitaDTO> getById(@PathVariable Long id) {
        ReceitaDTO receita = receitaService.getById(id);
        if (receita == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receita);
    }

    // Listar todas as receitas
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<List<ReceitaDTO>> findAll() {
        return ResponseEntity.ok(receitaService.findAll());
    }

    // Atualizar receita
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateReceitaById(
            @AuthenticationPrincipal User userLogado,
            @PathVariable Long id,
            @RequestBody ReceitaDTO receitaDTO) {

        java.util.UUID usuarioId = userLogado.getId();

        boolean updated = receitaService.updateReceitaById(id, receitaDTO, usuarioId);

        if (updated) {
            return ResponseEntity.ok("Receita atualizada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar receita
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteReceitaById(@PathVariable Long id) {
        receitaService.deleteReceitaById(id);
        return ResponseEntity.ok("Receita deletada com sucesso!");
    }
}
