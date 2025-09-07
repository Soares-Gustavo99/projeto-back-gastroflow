package com.fiec.projeto_back_gastroflow.features.aula.services;

import com.fiec.projeto_back_gastroflow.features.aula.dto.AulaDTO;

import java.util.List;

public interface AulaService {

    void createAula(AulaDTO aulaDTO);

    AulaDTO getById(Long id);

    List<AulaDTO> findAll();

    boolean updateAulaById(Long id, AulaDTO aulaDTO);

    void deleteAulaById(Long id);
}
