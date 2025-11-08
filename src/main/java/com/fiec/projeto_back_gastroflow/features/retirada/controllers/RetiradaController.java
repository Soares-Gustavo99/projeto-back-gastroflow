package com.fiec.projeto_back_gastroflow.features.retirada.controllers;

import com.fiec.projeto_back_gastroflow.features.retirada.dto.RetiradaDTO;
import com.fiec.projeto_back_gastroflow.features.retirada.services.impl.RetiradaServiceImpl;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Retiradas de Estoque", description = "Gerenciamento de movimentações de retirada de produtos do estoque.")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/api/retiradas")
public class RetiradaController {

    private final RetiradaServiceImpl retiradaService;

    // Criar Retirada (ADMIN e STANDARD)
    @Operation(summary = "Cria uma nova retirada de estoque", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retirada criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da retirada inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void createRetirada(@RequestBody RetiradaDTO retiradaDTO) {
        retiradaService.createRetirada(retiradaDTO);
    }

    // Buscar Retirada por ID (Long)
    @Operation(summary = "Busca uma retirada por ID", description = "Busca detalhada por ID. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retirada encontrada."),
            @ApiResponse(responseCode = "404", description = "Retirada não encontrada.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public RetiradaDTO getById(@Parameter(description = "ID da Retirada.") @RequestParam Long id) {
        return retiradaService.getById(id);
    }

    // Buscar Retiradas por ID do Produto (Long)
    @Operation(summary = "Lista todas as retiradas de um produto", description = "Retorna o histórico de retiradas de um produto específico. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de retiradas retornada com sucesso.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/produto", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public List<RetiradaDTO> getAllByProdutoId(@Parameter(description = "ID do Produto.") @RequestParam Long produtoId) {
        return retiradaService.getAllByProdutoId(produtoId);
    }

    // Listar todas as Retiradas
    @Operation(summary = "Lista todas as retiradas de estoque", description = "Retorna todas as movimentações de retirada. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de todas as retiradas retornada com sucesso.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/todas", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public List<RetiradaDTO> findAll() {
        return retiradaService.findAll();
    }

    // Atualizar Retirada por ID (Long)
    @Operation(summary = "Atualiza uma retirada de estoque por ID", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retirada atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Retirada não encontrada."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void updateRetiradaById(
            @Parameter(description = "ID da Retirada a ser atualizada.") @RequestParam Long id,
            @RequestBody RetiradaDTO retiradaDTO) {
        retiradaService.updateRetiradaById(id, retiradaDTO);
    }

    // Deletar Retirada por ID (Long)
    @Operation(summary = "Deleta uma retirada de estoque por ID", description = "Permitido apenas para 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Retirada deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Retirada não encontrada."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRetiradaById(@Parameter(description = "ID da Retirada a ser deletada.") @RequestParam Long id) {
        retiradaService.deleteRetiradaById(id);
    }
}