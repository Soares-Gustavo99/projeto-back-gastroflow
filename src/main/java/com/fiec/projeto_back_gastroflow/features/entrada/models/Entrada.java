package com.fiec.projeto_back_gastroflow.features.entrada.models;

import com.fiec.projeto_back_gastroflow.features.products.models.Produto;
import com.fiec.projeto_back_gastroflow.features.supplier.models.Supplier;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Entradas")
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Date dataEntrada;

    @Column(nullable = false)
    private Integer quantidade;

    @Column
    private String observacao;

    // Campos de Auditoria (baseados em Receita.java)
    @CreationTimestamp
    @Column(updatable = false) // Data de criação não deve ser atualizável
    private Date dataCadastro;

    @UpdateTimestamp
    private Date dataAlteracao;


    // Relação ManyToOne com Produto (Id Long)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Relação ManyToOne com Fornecedor (Id UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = true)
    private Supplier fornecedor;

    // Relação ManyToOne com Usuário (Id UUID)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Construtor parcial (sem ID e campos automáticos de auditoria)
    public Entrada(Date dataEntrada, Integer quantidade, String observacao, Produto produto, Supplier fornecedor, User user) {
        this.dataEntrada = dataEntrada;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.produto = produto;
        this.fornecedor = fornecedor;
        this.user = user;
    }
}