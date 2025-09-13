package com.fiec.projeto_back_gastroflow.features.products.models;

import com.fiec.projeto_back_gastroflow.features.receita.models.Receita;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name ="Produtos")
public class Produto {

    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column(nullable = false)
    private String unidadeMedida;

    @Column(nullable = false)
    private Date validade;

    @ManyToMany(mappedBy = "produtos")
    private List<Receita> receitas = new ArrayList<>();

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private User createdBy;

    private User updatedBy;


    public Produto(String nome, String categoria, Integer quantidadeEstoque, String unidadeMedida, Date validade) {
        setNome(nome);
        setCategoria(categoria);
        setQuantidadeEstoque(quantidadeEstoque);
        setUnidadeMedida(unidadeMedida);
        setValidade(validade);

    }
}
