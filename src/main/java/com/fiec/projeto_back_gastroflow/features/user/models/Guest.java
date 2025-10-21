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
public class Guest extends SystemUser{

    @ManyToMany(mappedBy = "guests")
    List<Admin> admins;

    String name;

}
