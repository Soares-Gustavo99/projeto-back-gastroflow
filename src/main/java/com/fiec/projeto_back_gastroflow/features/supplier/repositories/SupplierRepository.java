package com.fiec.projeto_back_gastroflow.features.supplier.repositories;

import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByRazaoSocial(String razaoSocial);
    Page<Supplier> findAll(Pageable pageable);
}