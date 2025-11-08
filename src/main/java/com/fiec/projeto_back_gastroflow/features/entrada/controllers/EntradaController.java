package com.fiec.projeto_back_gastroflow.features.entrada.controllers;

import com.fiec.projeto_back_gastroflow.features.entrada.dto.EntradaDTO;
import com.fiec.projeto_back_gastroflow.features.entrada.services.impl.EntradaServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
// IMPORTES SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Entradas de Estoque", description = "Gerenciamento de movimentações de entrada de produtos no estoque.")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/entradas")
public class EntradaController {

    private final EntradaServiceImpl entradaService;

    // Criar Entrada (ADMIN e STANDARD)
    @Operation(summary = "Cria uma nova entrada de estoque", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrada criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da entrada inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')") // Padrão ProdutoController
    public void createEntrada(@RequestBody EntradaDTO entradaDTO) {
        entradaService.createEntrada(entradaDTO);
    }

    // Buscar Entrada por ID (UUID)
    @Operation(summary = "Busca uma entrada por ID", description = "Busca detalhada por ID. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrada encontrada."),
            @ApiResponse(responseCode = "404", description = "Entrada não encontrada.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')") // Padrão ProdutoController
    public EntradaDTO getById(@Parameter(description = "ID da Entrada.") @RequestParam Long id) {
        return entradaService.getById(id);
    }

    // Buscar Entradas por ID do Produto (Long)
    @Operation(summary = "Lista todas as entradas de um produto", description = "Retorna o histórico de entradas de um produto específico. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de entradas retornada com sucesso.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/produto", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')") // Padrão ProdutoController
    public List<EntradaDTO> getAllByProdutoId(@Parameter(description = "ID do Produto.") @RequestParam Long produtoId) {
        return entradaService.getAllByProdutoId(produtoId);
    }

    // Listar todas as Entradas
    @Operation(summary = "Lista todas as entradas de estoque", description = "Retorna todas as movimentações de entrada. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de todas as entradas retornada com sucesso.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/todas", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')") // Padrão ProdutoController
    public List<EntradaDTO> findAll() {
        return entradaService.findAll();
    }

    // Atualizar Entrada por ID (UUID)
    @Operation(summary = "Atualiza uma entrada de estoque por ID", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrada atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Entrada não encontrada."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')") // Padrão ProdutoController
    public void updateEntradaById(
            @Parameter(description = "ID da Entrada a ser atualizada.") @RequestParam Long id,
            @RequestBody EntradaDTO entradaDTO) {
        entradaService.updateEntradaById(id, entradaDTO);
    }

    // Deletar Entrada por ID (UUID)
    @Operation(summary = "Deleta uma entrada de estoque por ID", description = "Permitido apenas para 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrada deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Entrada não encontrada."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEntradaById(@Parameter(description = "ID da Entrada a ser deletada.") @RequestParam Long id) {
        entradaService.deleteEntradaById(id);
    }
}