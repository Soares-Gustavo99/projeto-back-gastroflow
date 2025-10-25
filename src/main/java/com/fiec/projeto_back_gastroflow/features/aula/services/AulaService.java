package com.fiec.projeto_back_gastroflow.features.aula.services;

import com.fiec.projeto_back_gastroflow.features.aula.dto.AulaDTO;

import java.util.List;
import java.util.UUID;

public interface AulaService {

    void createAula(AulaDTO aulaDTO, UUID usuarioId);

    AulaDTO getById(Long id);

    List<AulaDTO> findAll();

    boolean updateAulaById(Long id, AulaDTO aulaDTO, UUID usuarioId);

    void deleteAulaById(Long id);

}