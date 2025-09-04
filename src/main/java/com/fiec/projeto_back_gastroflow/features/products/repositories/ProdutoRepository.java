package com.fiec.projeto_back_gastroflow.features.products.repositories;


import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNome(String nome);

    List<Produto> findAllByNome(String nome);
}
