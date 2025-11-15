package com.fiec.projeto_back_gastroflow.features.products.repositories;

import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.products.models.ProdutoPagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoCustomRepository {
    Page<Produto> findProdutos(ProdutoSearch produtoSearch);
}
