package com.fiec.projeto_back_gastroflow.features.products.repositories;

import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSearch;
import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoSummaryDTO;
import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoCustomRepository {
    List<ProdutoSummaryDTO> findProdutos(ProdutoSearch produtoSearch);
}
