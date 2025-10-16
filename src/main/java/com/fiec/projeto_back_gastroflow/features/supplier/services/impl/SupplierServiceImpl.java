package com.fiec.projeto_back_gastroflow.features.supplier.services.impl;

import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.supplier.repositories.SupplierRepository;
import com.fiec.projeto_back_gastroflow.features.supplier.services.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier save(Supplier supplier) {
        // Validação adicional: Verifica se já existe um fornecedor com a mesma razão social
        if (supplierRepository.findByRazaoSocial(supplier.getRazaoSocial()).isPresent()) {
            throw new RuntimeException("Já existe um fornecedor com a Razão Social: " + supplier.getRazaoSocial());
        }
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<Supplier> findById(UUID id) {
        return supplierRepository.findById(id);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier update(UUID id, Supplier updatedSupplier) {
        return supplierRepository.findById(id).map(supplier -> {
            // Atualiza os campos
            supplier.setRazaoSocial(updatedSupplier.getRazaoSocial());
            supplier.setNomeFantasia(updatedSupplier.getNomeFantasia());
            supplier.setTelefone(updatedSupplier.getTelefone());
            supplier.setEmail(updatedSupplier.getEmail());
            supplier.setEndereco(updatedSupplier.getEndereco());

            return supplierRepository.save(supplier);
        }).orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + id));
    }

    @Override
    public void deleteById(UUID id) {
        supplierRepository.deleteById(id);
    }
}
