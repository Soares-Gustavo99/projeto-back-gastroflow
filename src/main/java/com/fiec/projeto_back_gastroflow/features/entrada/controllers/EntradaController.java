package com.fiec.projeto_back_gastroflow.features.entrada.controllers;

import com.fiec.projeto_back_gastroflow.features.entrada.dto.EntradaDTO;
import com.fiec.projeto_back_gastroflow.features.entrada.services.EntradaService;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/entradas")
public class EntradaController {

    private final EntradaService entradaService;

    // Criar Entrada (ADMIN e STANDARD)
    // Padrão ReceitaController: ResponseEntity<String>, @AuthenticationPrincipal
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public ResponseEntity<String> createEntrada(
            @AuthenticationPrincipal User userLogado,
            @RequestBody EntradaDTO entradaDTO) {

        java.util.UUID usuarioId = userLogado.getId();
        entradaService.createEntrada(entradaDTO, usuarioId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Entrada cadastrada com sucesso!");
    }

    // Buscar Entrada por ID (Long)
    // Padrão ReceitaController: ResponseEntity<EntradaDTO>, @PathVariable
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public ResponseEntity<EntradaDTO> getById(@PathVariable Long id) {
        EntradaDTO entrada = entradaService.getById(id);
        if (entrada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entrada);
    }

    // Buscar Entradas por ID do Produto (Long)
    // Padrão ReceitaController: ResponseEntity<List<EntradaDTO>>, @PathVariable
    @GetMapping(value = "/produto/{produtoId}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<List<EntradaDTO>> getAllByProdutoId(@PathVariable Long produtoId) {
        return ResponseEntity.ok(entradaService.getAllByProdutoId(produtoId));
    }

    // Listar todas as Entradas
    // Padrão ReceitaController: ResponseEntity<List<EntradaDTO>>
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public ResponseEntity<List<EntradaDTO>> findAll() {
        return ResponseEntity.ok(entradaService.findAll());
    }

    // Atualizar Entrada por ID (Long)
    // Padrão ReceitaController: ResponseEntity<String>, @PathVariable, @AuthenticationPrincipal
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public ResponseEntity<String> updateEntradaById(
            @AuthenticationPrincipal User userLogado,
            @PathVariable Long id,
            @RequestBody EntradaDTO entradaDTO) {

        java.util.UUID usuarioId = userLogado.getId();

        boolean updated = entradaService.updateEntradaById(id, entradaDTO, usuarioId);

        if (updated) {
            return ResponseEntity.ok("Entrada atualizada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar Entrada por ID (Long)
    // Padrão ReceitaController: ResponseEntity<String>, @PathVariable, 204 No Content
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public ResponseEntity<String> deleteEntradaById(@PathVariable Long id) {
        entradaService.deleteEntradaById(id);
        return ResponseEntity.noContent().build();
    }
}