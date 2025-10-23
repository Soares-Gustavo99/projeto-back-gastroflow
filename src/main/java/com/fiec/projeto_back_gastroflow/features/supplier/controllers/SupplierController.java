package com.fiec.projeto_back_gastroflow.features.supplier.controllers;

import com.fiec.projeto_back_gastroflow.features.supplier.dto.SupplierDto;
import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.supplier.services.SupplierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/suppliers")
@AllArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    // Converte DTO para Model para salvar
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

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public List<Supplier> getAllSuppliers() {
        return supplierService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD','GUEST')")
    public Supplier getSupplierById(@PathVariable UUID id) {
        return supplierService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor não encontrado"));
    }

    // Converte DTO para Model para atualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public Supplier updateSupplier(@PathVariable UUID id, @Valid @RequestBody SupplierDto supplierDto) {
        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setRazaoSocial(supplierDto.getRazaoSocial());
        updatedSupplier.setNomeFantasia(supplierDto.getNomeFantasia());
        updatedSupplier.setTelefone(supplierDto.getTelefone());
        updatedSupplier.setEmail(supplierDto.getEmail());
        updatedSupplier.setEndereco(supplierDto.getEndereco());

        return supplierService.update(id, updatedSupplier);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','STANDARD')")
    public void deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteById(id);
    }
}
