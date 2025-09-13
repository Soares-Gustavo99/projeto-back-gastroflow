package com.fiec.projeto_back_gastroflow.features.products.services;

import com.fiec.projeto_back_gastroflow.features.products.dto.ProdutoDTO;
import com.fiec.projeto_back_gastroflow.features.user.models.User;

import java.util.List;

public interface ProdutoService  {

    void createProduto(ProdutoDTO produtoDTO, User user);

    ProdutoDTO getById(Long id);

    List<ProdutoDTO> getAllByNome(String nome);

    List<ProdutoDTO> findAll();

    boolean updateProdutoById(Long id, ProdutoDTO produtoDTO);

    void deleteProdutoById(Long id);
}
