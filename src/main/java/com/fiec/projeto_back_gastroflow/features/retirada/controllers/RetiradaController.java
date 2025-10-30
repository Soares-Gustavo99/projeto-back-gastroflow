package com.fiec.projeto_back_gastroflow.features.retirada.controllers;

import com.fiec.projeto_back_gastroflow.features.retirada.dto.RetiradaDTO;
import com.fiec.projeto_back_gastroflow.features.retirada.services.impl.RetiradaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/retiradas")
public class RetiradaController {

    private final RetiradaServiceImpl retiradaService;

    // Criar Retirada (ADMIN e STANDARD)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void createRetirada(@RequestBody RetiradaDTO retiradaDTO) {
        retiradaService.createRetirada(retiradaDTO);
    }

    // Buscar Retirada por ID (Long)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public RetiradaDTO getById(@RequestParam Long id) {
        return retiradaService.getById(id);
    }

    // Buscar Retiradas por ID do Produto (Long)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/produto", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public List<RetiradaDTO> getAllByProdutoId(@RequestParam Long produtoId) {
        return retiradaService.getAllByProdutoId(produtoId);
    }

    // Listar todas as Retiradas
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/todas", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public List<RetiradaDTO> findAll() {
        return retiradaService.findAll();
    }

    // Atualizar Retirada por ID (Long)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void updateRetiradaById(@RequestParam Long id, @RequestBody RetiradaDTO retiradaDTO) {
        retiradaService.updateRetiradaById(id, retiradaDTO);
    }

    // Deletar Retirada por ID (Long)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRetiradaById(@RequestParam Long id) {
        retiradaService.deleteRetiradaById(id);
    }
}