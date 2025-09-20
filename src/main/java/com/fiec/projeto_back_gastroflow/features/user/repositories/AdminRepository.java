package com.fiec.projeto_back_gastroflow.features.user.repositories;


import com.fiec.projeto_back_gastroflow.features.user.models.Admin;
import com.fiec.projeto_back_gastroflow.features.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUser(User user);
}
