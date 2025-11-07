package com.fiec.projeto_back_gastroflow.features.aula.models;

import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private Date data;

    private String instrutor;

    private String materia;

    private Integer ano;

    private Integer semestre;

    private Integer modulo;

    private String periodo;

    //OneToMany


    @OneToMany
    @JoinTable(
            name = "aula_receita",
            joinColumns = @JoinColumn(name = "aula_id"),
            inverseJoinColumns = @JoinColumn(name = "receita_id")
    )
    //IMPORTAR PRODUTO AQUI
    private List<Receita> receitas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "fk_usuario_id")
    private User user;
    //IMPORTAR PRODUTO AQUI

}
