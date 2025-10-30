package com.fiec.projeto_back_gastroflow.features.entrada.services;

import com.fiec.projeto_back_gastroflow.features.entrada.dto.EntradaDTO;

import java.util.List;
import java.util.UUID;

// Interface seguindo o padrão de ProdutoService
public interface EntradaService {

    void createEntrada(EntradaDTO entradaDTO);

    EntradaDTO getById(Long id); // <--- ID UUID

    List<EntradaDTO> getAllByProdutoId(Long produtoId);

    List<EntradaDTO> findAll();

    boolean updateEntradaById(Long id, EntradaDTO entradaDTO); // <--- ID UUID

    void deleteEntradaById(Long id); // <--- ID UUID
}