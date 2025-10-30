package com.fiec.projeto_back_gastroflow.features.entrada.repositories;

import com.fiec.projeto_back_gastroflow.features.entrada.models.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    // Exemplo de método de busca customizado, seguindo o padrão de ProdutoRepository
    List<Entrada> findAllByProdutoId(Long produtoId);
}