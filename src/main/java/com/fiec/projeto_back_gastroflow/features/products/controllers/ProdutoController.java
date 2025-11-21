package com.fiec.projeto_back_gastroflow.features.products.controllers;


import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoPagedResponseDTO;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.services.impl.ProdutoServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Produtos", description = "Gerenciamento de cadastro de produtos e itens de estoque.")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/produtos")
public class ProdutoController {

    private final ProdutoServiceImpl produtoService;

    @Operation(summary = "Cria um novo produto", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados do produto inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void createProduto(@RequestBody ProdutoDTO produtoDTO){

        produtoService.createProduto(produtoDTO);
    }

    @Operation(summary = "Importa produtos via CSV",
            description = "Permite o upload de um arquivo CSV para criação em massa de produtos no estoque. Permitido apenas para 'ADMIN'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos importados com sucesso. (Retorno 'void' com Status 200)"),
            @ApiResponse(responseCode = "400", description = "O arquivo está vazio, ou o formato dos dados (ENUMs, números) no CSV está inválido."),
            @ApiResponse(responseCode = "403", description = "Acesso negado (Usuário não é ADMIN)."),
            @ApiResponse(responseCode = "500", description = "Erro interno no processamento ou persistência dos dados.")
    })
    @ResponseStatus(HttpStatus.OK) // Status 200 OK para operações bem-sucedidas sem retorno de corpo.
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/csv", consumes = "multipart/form-data") // Especifica o tipo de requisição
    public void createProductsFromCsv(
            @Parameter(description = "Arquivo CSV contendo os dados dos produtos. Deve ser enviado como 'multipart/form-data'.", required = true)
            @RequestParam("inputFile") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo de entrada não pode estar vazio.");
        }
        produtoService.createProductsFromCsv(file.getInputStream());
    }

    @Operation(summary = "Busca um produto por ID", description = "Busca detalhada por ID. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public ProdutoDTO getById(@Parameter(description = "ID do Produto.") @RequestParam Long id){
        return produtoService.getById(id);
    }

    @Operation(summary = "Busca produtos por nome", description = "Busca produtos que contenham o nome fornecido. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "nome", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD', 'GUEST')")
    public List<ProdutoDTO> getAllByNome(@Parameter(description = "Parte ou nome completo do produto.") @RequestParam String nome){
        return produtoService.getAllByNome(nome);
    }

    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados. Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de todos os produtos retornada com sucesso.")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "produtos", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public List<ProdutoDTO> findAll(){
        return produtoService.findAll();
    }

    @Operation(summary = "Atualiza um produto por ID", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void updateProdutoById(
            @Parameter(description = "ID do Produto a ser atualizado.") @RequestParam Long id,
            @RequestBody ProdutoDTO produtoDTO){
        produtoService.updateProdutoById(id, produtoDTO);
    }

    @Operation(summary = "Deleta um produto por ID", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping()
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void deleteProdutoById(@Parameter(description = "ID do Produto a ser deletado.") @RequestParam Long id){
        produtoService.deleteProdutoById(id);
    }

    @GetMapping("/filters/all")
    public ProdutoPagedResponseDTO getProductById(ProdutoSearch productSearch){
        return produtoService.findAllWithQueries(productSearch);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/photo") // Usa o ID do produto no path
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void insertProdutoImage(@PathVariable Long id,
                                   @RequestParam("image") MultipartFile image){

        produtoService.insertProdutoImage(id, image);
    }
}

