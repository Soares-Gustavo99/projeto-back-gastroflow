package com.fiec.projeto_back_gastroflow.features.aula.controllers;

import com.fiec.projeto_back_gastroflow.features.aula.dto.AulaDTO;
import com.fiec.projeto_back_gastroflow.features.aula.services.impl.AulaServiceImpl;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/aulas")
@AllArgsConstructor
public class AulaController {

    private final AulaServiceImpl aulaService;

    // Criar uma aula
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STANDARD')")
    public ResponseEntity<String> createAula(@AuthenticationPrincipal User userLogado, @RequestBody AulaDTO aulaDTO) {
        UUID usuarioId = userLogado.getId();
        aulaService.createAula(aulaDTO, usuarioId);
        return ResponseEntity.ok("Aula cadastrada com sucesso!");
    }

    // Buscar aula por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<AulaDTO> getById(@PathVariable Long id) {
        AulaDTO aula = aulaService.getById(id);
        if (aula == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aula);
    }

    // Listar todas as aulas
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public ResponseEntity<List<AulaDTO>> findAll() {
        return ResponseEntity.ok(aulaService.findAll());
    }

    // Atualizar aula
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateAulaById(
            @AuthenticationPrincipal User userLogado,
            @PathVariable Long id,
            @RequestBody AulaDTO aulaDTO) {

        UUID usuarioId = userLogado.getId();

        boolean updated = aulaService.updateAulaById(id, aulaDTO, usuarioId);

        if (updated) {
            return ResponseEntity.ok("Aula atualizada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar aula
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAulaById(@PathVariable Long id) {
        aulaService.deleteAulaById(id);
        return ResponseEntity.ok("Aula deletada com sucesso!");
    }
}