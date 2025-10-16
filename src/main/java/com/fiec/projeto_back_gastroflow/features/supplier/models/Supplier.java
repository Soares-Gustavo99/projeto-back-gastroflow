package com.fiec.projeto_back_gastroflow.features.supplier.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String razaoSocial;

    @Column
    private String nomeFantasia;

    @Column
    private String telefone;

    @Column
    private String email;

    @Column
    private String endereco;

}