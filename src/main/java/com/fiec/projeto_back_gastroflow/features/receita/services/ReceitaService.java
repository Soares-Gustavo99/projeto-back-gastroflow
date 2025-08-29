package com.fiec.projeto_back_gastroflow.features.receita.services;

import com.fiec.projeto_back_gastroflow.features.receita.dto.ReceitaDTO;

import java.util.List;

public interface ReceitaService {

    void createReceita(ReceitaDTO receitaDTO);

    ReceitaDTO getById(Long id);

    List<ReceitaDTO> findAll();

    boolean updateReceitaById(Long id, ReceitaDTO receitaDTO);

    void deleteReceitaById(Long id);
}