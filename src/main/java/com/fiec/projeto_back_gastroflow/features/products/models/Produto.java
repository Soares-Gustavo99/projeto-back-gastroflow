package com.fiec.projeto_back_gastroflow.features.products.models;

import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="Produtos")
public class Produto {

    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Categoria categoria;

    @Column
    private Integer quantidadeEstoque;

    @Column(nullable = false)
    private UnidadeMedida unidadeMedida;

    @Column(nullable = false)
    private Date validade;

    @ManyToMany(mappedBy = "produtos")
    private List<Receita> receitas = new ArrayList<>();



    public Produto(String nome, Categoria categoria, Integer quantidadeEstoque, UnidadeMedida unidadeMedida, Date validade) {
        setNome(nome);
        setCategoria(categoria);
        setQuantidadeEstoque(quantidadeEstoque);
        setUnidadeMedida(unidadeMedida);
        setValidade(validade);

    }
}
