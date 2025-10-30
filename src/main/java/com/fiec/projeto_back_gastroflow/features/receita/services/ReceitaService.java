package com.fiec.projeto_back_gastroflow.features.receita.services;

import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;

import java.util.List;
import java.util.UUID;

public interface ReceitaService {

    void createReceita(ReceitaDTO receitaDTO, UUID usuarioID);

    ReceitaDTO getById(Long id);

    List<ReceitaDTO> findAll();

    boolean updateReceitaById(Long id, ReceitaDTO receitaDTO, java.util.UUID usuarioId);

    void deleteReceitaById(Long id);

}