package com.fiec.projeto_back_gastroflow.features.entrada.repositories;

import com.fiec.projeto_back_gastroflow.features.entrada.models.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> { // Usando Long

    // Método de busca customizado
    List<Entrada> findAllByProdutoId(Long produtoId);
}