package com.fiec.projeto_back_gastroflow.features.supplier.controllers;

import com.fiec.projeto_back_gastroflow.features.supplier.dto.SupplierDto;
import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.supplier.services.SupplierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
// IMPORTES SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.UUID;

@Tag(name = "Fornecedores", description = "Gerenciamento de fornecedores (Suppliers).")
@RestController
@RequestMapping("/v1/api/suppliers")
@AllArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    // Converte DTO para Model para salvar
    @Operation(summary = "Cria um novo fornecedor", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fornecedor criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados do fornecedor inválidos."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public Supplier createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setRazaoSocial(supplierDto.getRazaoSocial());
        supplier.setNomeFantasia(supplierDto.getNomeFantasia());
        supplier.setTelefone(supplierDto.getTelefone());
        supplier.setEmail(supplierDto.getEmail());
        supplier.setEndereco(supplierDto.getEndereco());

        return supplierService.save(supplier);
    }

    @Operation(summary = "Lista todos os fornecedores", description = "Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de fornecedores retornada com sucesso.")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")

    public Page<Supplier> getAllSuppliers(
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        return supplierService.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Operation(summary = "Busca um fornecedor por ID", description = "Permitido para 'ADMIN', 'STANDARD' ou 'GUEST'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor encontrado."),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado.")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public Supplier getSupplierById(@Parameter(description = "ID do Fornecedor.") @PathVariable Long id) {
        return supplierService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
    }

    // Converte DTO para Model para atualizar
    @Operation(summary = "Atualiza um fornecedor por ID", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public Supplier updateSupplier(
            @Parameter(description = "ID do Fornecedor a ser atualizado.") @PathVariable Long id,
            @Valid @RequestBody SupplierDto supplierDto) {
        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setRazaoSocial(supplierDto.getRazaoSocial());
        updatedSupplier.setNomeFantasia(supplierDto.getNomeFantasia());
        updatedSupplier.setTelefone(supplierDto.getTelefone());
        updatedSupplier.setEmail(supplierDto.getEmail());
        updatedSupplier.setEndereco(supplierDto.getEndereco());

        return supplierService.update(id, updatedSupplier);
    }

    @Operation(summary = "Deleta um fornecedor por ID", description = "Permitido para 'ADMIN' e 'STANDARD'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado."),
            @ApiResponse(responseCode = "403", description = "Acesso negado.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void deleteSupplier(@Parameter(description = "ID do Fornecedor a ser deletado.") @PathVariable Long id) {
        supplierService.deleteById(id);
    }
}