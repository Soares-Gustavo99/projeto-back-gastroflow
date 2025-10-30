package com.fiec.projeto_back_gastroflow.features.retirada.models;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Retiradas")
public class Retirada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID como Long, conforme solicitado

    @Column(nullable = false)
    private Date dataRetirada;

    @Column(nullable = false)
    private Integer quantidade;

    @Column
    private String observacao;

    // Relação ManyToOne com Produto (Produto tem Id Long)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Relação ManyToOne com Usuário (User tem id UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Construtor para uso no Service ao criar
    public Retirada(Date dataRetirada, Integer quantidade, String observacao, Produto produto, User user) {
        this.dataRetirada = dataRetirada;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.produto = produto;
        this.user = user;
    }
}