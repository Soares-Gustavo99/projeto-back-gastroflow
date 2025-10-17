package com.fiec.projeto_back_gastroflow.features.receita.controllers;

import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;
import com.fiec.projeto_back_gastroflow.features.receita.services.impl.ReceitaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receita")
@AllArgsConstructor
public class ReceitaController {

    private final ReceitaServiceImpl receitaService;

    // Criar uma receita
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createReceita(@RequestBody ReceitaDTO receitaDTO) {
        receitaService.createReceita(receitaDTO);
        return ResponseEntity.ok("Receita cadastrada com sucesso!");
    }

    // Buscar receita por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public ResponseEntity<ReceitaDTO> getById(@PathVariable Long id) {
        ReceitaDTO receita = receitaService.getById(id);
        if (receita == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receita);
    }

    // Listar todas as receitas
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public ResponseEntity<List<ReceitaDTO>> findAll() {
        return ResponseEntity.ok(receitaService.findAll());
    }

    // Atualizar receita
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateReceitaById(@PathVariable Long id, @RequestBody ReceitaDTO receitaDTO) {
        boolean updated = receitaService.updateReceitaById(id, receitaDTO);
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
