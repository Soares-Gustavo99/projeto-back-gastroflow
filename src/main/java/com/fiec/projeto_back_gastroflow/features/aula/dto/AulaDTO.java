package com.fiec.projeto_back_gastroflow.features.aula.dto;

import com.fiec.projeto_back_gastroflow.features.aulaReceitas.AulaReceitaItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AulaDTO {

    private Long id;

    private String nome;

    private String descricao;

    private Date data;

    private String instrutor;

    private String materia;

    private Integer ano;

    private Integer semestre;

    private Integer modulo;

    private String periodo;

    private UUID userId;

    private List<AulaReceitaItemDTO> receitas;}
