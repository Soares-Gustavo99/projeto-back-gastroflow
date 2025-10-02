package com.fiec.projeto_back_gastroflow.features.user.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends SystemUser {

    @ManyToMany
    @JoinTable(
            name = "guest_admin_relationship",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    List<Guest> guests;
}


