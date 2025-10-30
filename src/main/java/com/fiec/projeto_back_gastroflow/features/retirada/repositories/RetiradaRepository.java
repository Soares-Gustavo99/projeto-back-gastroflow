package com.fiec.projeto_back_gastroflow.features.retirada.repositories;

import com.fiec.projeto_back_gastroflow.features.retirada.models.Retirada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetiradaRepository extends JpaRepository<Retirada, Long> {

    // Método para buscar todas as retiradas de um produto específico
    List<Retirada> findAllByProdutoId(Long produtoId);
}