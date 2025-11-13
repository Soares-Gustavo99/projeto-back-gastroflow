package com.fiec.projeto_back_gastroflow.features.entrada.repositories;

import com.fiec.projeto_back_gastroflow.features.entrada.models.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    // O nome do relacionamento no model Entrada é 'produtos'
    @Query("SELECT e FROM Entrada e JOIN e.produtos ep WHERE ep.produto.id = :produtoId")
    List<Entrada> findAllByProdutoId(@Param("produtoId") Long produtoId);

}