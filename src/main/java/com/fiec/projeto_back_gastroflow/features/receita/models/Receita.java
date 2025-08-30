package com.fiec.projeto_back_gastroflow.features.receita.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String tempoPreparo;
    private String textoPreparo;

    @ManyToMany
    @JoinTable(
            name = "receita_produto",
            joinColumns = @JoinColumn(name = "receita_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    //IMPORTAR PRODUTO AQUI
    private List<Produto> produtos = new ArrayList<>();
}
