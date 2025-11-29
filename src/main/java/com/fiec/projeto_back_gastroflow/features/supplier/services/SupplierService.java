package com.fiec.projeto_back_gastroflow.features.supplier.services;

import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierService {
    Supplier save(Supplier supplier);
    Optional<Supplier> findById(Long id);
    Supplier update(Long id, Supplier updatedSupplier);
    void deleteById(Long id);
    Page<Supplier> findAllPageble(Pageable pageable); // Seu nome preferido
    List<Supplier> getAll();
}
