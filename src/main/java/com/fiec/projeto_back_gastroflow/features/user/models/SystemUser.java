package com.fiec.projeto_back_gastroflow.features.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
// @Entity // Adicione esta anotação
// @Inheritance(strategy = InheritanceType.JOINED) // Ou outro tipo de estratégia, como SINGLE_TABLE ou TABLE_PER_CLASS
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @OneToOne
    User user;
}