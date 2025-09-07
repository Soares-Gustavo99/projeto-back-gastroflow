package com.fiec.projeto_back_gastroflow.features.aula.services.impl;


import com.fiec.projeto_back_gastroflow.features.aula.dto.AulaDTO;
import com.fiec.projeto_back_gastroflow.features.aula.models.Aula;
import com.fiec.projeto_back_gastroflow.features.aula.repositories.AulaRepository;
import com.fiec.projeto_back_gastroflow.features.aula.services.AulaService;
import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.receita.repositories.ReceitaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final ReceitaRepository receitaRepository;

    @Override
    public void createAula(AulaDTO aulaDTO) {
        Aula aula = new Aula();
        aula.setNome(aulaDTO.getNome());
        aula.setDescricao(aulaDTO.getDescricao());
        aula.setMateria(aulaDTO.getMateria());
        aula.setData(aulaDTO.getData());
        aula.setInstrutor(aulaDTO.getInstrutor());

        if (aulaDTO.getReceitaIds() != null) {
            List<Receita> receitas = receitaRepository.findAllById(aulaDTO.getReceitaIds());
            aula.setReceitas(receitas);
        }

        aulaRepository.save(aula);

    }
    @Override
    public AulaDTO getById(Long id) {
        return aulaRepository.findById(id).map( aula -> {
           AulaDTO dto = new AulaDTO();
           dto.setId(aula.getId());
           dto.setNome(aula.getNome());
           dto.setMateria(aula.getMateria());
           dto.setInstrutor(aula.getInstrutor());
           dto.setData(aula.getData());
           dto.setDescricao(aula.getDescricao());

           dto.setReceitaIds(
                   aula.getReceitas().stream().map(Receita::getId).toList()
           );
           return dto;
        }).orElse(null);
    }

    @Override
    public List<AulaDTO> findAll() {
        return aulaRepository.findAll().stream().map(aula -> {
            AulaDTO dto = new AulaDTO();
            dto.setId(aula.getId());
            dto.setNome(aula.getNome());
            dto.setMateria(aula.getMateria());
            dto.setInstrutor(aula.getInstrutor());
            dto.setData(aula.getData());
            dto.setDescricao(aula.getDescricao());

            dto.setReceitaIds(
                    aula.getReceitas().stream().map(Receita::getId).toList()
            );
            return dto;
        }).toList();
    }

    @Override
    public boolean updateAulaById(Long id, AulaDTO aulaDTO) {
        return aulaRepository.findById(id).map(aula -> {
            aula.setNome(aulaDTO.getNome());
            aula.setMateria(aulaDTO.getMateria());
            aula.setInstrutor(aulaDTO.getInstrutor());
            aula.setData(aulaDTO.getData());
            aula.setDescricao(aulaDTO.getDescricao());

            if (aulaDTO.getReceitaIds() != null) {
                List<Receita> receitas = receitaRepository.findAllById(aulaDTO.getReceitaIds());
                aula.setReceitas(receitas);
            }

            aulaRepository.save(aula);
            return true;
        }).orElse(false);
    }

    @Override
    public void deleteAulaById(Long id){aulaRepository.deleteById(id);}
}
