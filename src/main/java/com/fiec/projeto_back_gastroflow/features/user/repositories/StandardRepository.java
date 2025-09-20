package com.fiec.projeto_back_gastroflow.features.user.repositories;

import com.fiec.projeto_back_gastroflow.features.user.models.Standard;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardRepository extends JpaRepository<Standard, String> {
    Optional<Standard> findByUser(User user);
}

