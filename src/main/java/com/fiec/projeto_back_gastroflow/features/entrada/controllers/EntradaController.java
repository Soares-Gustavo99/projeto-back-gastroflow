package com.fiec.projeto_back_gastroflow.features.entrada.controllers;

import com.fiec.projeto_back_gastroflow.features.entrada.dto.EntradaDTO;
import com.fiec.projeto_back_gastroflow.features.entrada.services.impl.EntradaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/entradas")
public class EntradaController {

    private final EntradaServiceImpl entradaService;

    // Criar Entrada (ADMIN e STANDARD)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')") // Padrão ProdutoController
    public void createEntrada(@RequestBody EntradaDTO entradaDTO) {
        entradaService.createEntrada(entradaDTO);
    }

    // Buscar Entrada por ID (UUID)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')") // Padrão ProdutoController
    public EntradaDTO getById(@RequestParam Long id) {
        return entradaService.getById(id);
    }

    // Buscar Entradas por ID do Produto (Long)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/produto", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')") // Padrão ProdutoController
    public List<EntradaDTO> getAllByProdutoId(@RequestParam Long produtoId) {
        return entradaService.getAllByProdutoId(produtoId);
    }

    // Listar todas as Entradas
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/todas", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')") // Padrão ProdutoController
    public List<EntradaDTO> findAll() {
        return entradaService.findAll();
    }

    // Atualizar Entrada por ID (UUID)
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')") // Padrão ProdutoController
    public void updateEntradaById(@RequestParam Long id, @RequestBody EntradaDTO entradaDTO) {
        entradaService.updateEntradaById(id, entradaDTO);
    }

    // Deletar Entrada por ID (UUID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')") // Padrão ProdutoController
    public void deleteEntradaById(@RequestParam Long id) {
        entradaService.deleteEntradaById(id);
    }
}