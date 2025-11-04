package com.fiec.projeto_back_gastroflow.features.entrada.services;

import com.fiec.projeto_back_gastroflow.features.entrada.dto.EntradaDTO;

import java.util.List;
import java.util.UUID;

public interface EntradaService {

    // Adiciona o UUID do usuário logado para auditoria (padrão ReceitaService)
    void createEntrada(EntradaDTO entradaDTO, UUID usuarioID);

    EntradaDTO getById(Long id);

    List<EntradaDTO> getAllByProdutoId(Long produtoId);

    List<EntradaDTO> findAll();

    // Adiciona o UUID do usuário logado para auditoria (padrão ReceitaService)
    boolean updateEntradaById(Long id, EntradaDTO entradaDTO, UUID usuarioId);

    void deleteEntradaById(Long id);
}