package com.fiec.projeto_back_gastroflow.features.user.models;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
//@Table(name = "users")
@Data // Lombok: Gera Getters, Setters, toString, etc.
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserLevel accessLevel;

    @Column
    private String picture;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(accessLevel);
    }

    @Override
    public String getUsername() {
        return email;
    }
}