package com.fiec.projeto_back_gastroflow.features.products.repositories;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoDetailsRepository extends JpaRepository<Produto, Long> {

    List<Produto> findAllByNomeContainingIgnoreCase(String nome);
}
