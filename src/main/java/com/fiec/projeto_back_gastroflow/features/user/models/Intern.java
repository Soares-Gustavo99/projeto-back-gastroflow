package com.fiec.projeto_back_gastroflow.features.user.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Intern {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    String nome;

    Long rm;

    @OneToOne
    User user;

}
