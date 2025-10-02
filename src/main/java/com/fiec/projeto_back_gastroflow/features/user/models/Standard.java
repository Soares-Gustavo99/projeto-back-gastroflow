package com.fiec.projeto_back_gastroflow.features.user.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Standard extends SystemUser {

    @ManyToMany
    @JoinTable(
            name = "supplier_admin_relationship",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    List<Guest> guests;
}
