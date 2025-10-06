package com.fiec.projeto_back_gastroflow.features.user.models;

import org.springframework.security.core.GrantedAuthority;

public enum UserLevel implements GrantedAuthority {
    ROLE_GUEST,
    ROLE_ADMIN,
    ROLE_STANDARD;

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}