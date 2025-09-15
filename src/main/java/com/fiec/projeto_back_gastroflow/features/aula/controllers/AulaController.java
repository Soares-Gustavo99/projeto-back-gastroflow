package com.fiec.projeto_back_gastroflow.features.aula.controllers;


import com.fiec.projeto_back_gastroflow.features.aula.dto.AulaDTO;
import com.fiec.projeto_back_gastroflow.features.aula.services.impl.AulaServiceImpl;
import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aulas")
@AllArgsConstructor
public class AulaController {

    private final AulaServiceImpl aulaService;

    //CRIAR AULA
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAula(@RequestBody AulaDTO aulaDTO) {
        aulaService.createAula(aulaDTO);
        return ResponseEntity.ok("Aula cadastrada com sucesso!");
    }
    //LISTAR AULA POR ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<AulaDTO> getById(@PathVariable Long id) {
        AulaDTO aula = aulaService.getById(id);
        if (aula == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aula);
    }

    //LISTAR TODAS AS AULAS
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<AulaDTO>> findAll() {
        return ResponseEntity.ok(aulaService.findAll());
    }

    //ATUALIZAR AULA
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateAulaById(@PathVariable Long id, @RequestBody AulaDTO aulaDTO) {
        boolean updated = aulaService.updateAulaById(id, aulaDTO);
        if (updated) {
            return ResponseEntity.ok("Aula atualizada com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETAR AULA
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAulaById(@PathVariable Long id) {
        aulaService.deleteAulaById(id);
        return ResponseEntity.ok("Aula deletada com sucesso!");
    }

}
