package com.fiec.projeto_back_gastroflow.features.supplier.services;

import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierService {
    Supplier save(Supplier supplier);
    Optional<Supplier> findById(Long id);
    List<Supplier> findAll();
    Supplier update(Long id, Supplier updatedSupplier);
    void deleteById(Long id);
}
