package com.fiec.projeto_back_gastroflow.features.retirada.services;

import com.fiec.projeto_back_gastroflow.features.retirada.dto.RetiradaDTO;

import java.util.List;

public interface RetiradaService {

    void createRetirada(RetiradaDTO retiradaDTO);

    RetiradaDTO getById(Long id);

    List<RetiradaDTO> getAllByProdutoId(Long produtoId);

    List<RetiradaDTO> findAll();

    boolean updateRetiradaById(Long id, RetiradaDTO retiradaDTO);

    void deleteRetiradaById(Long id);
}