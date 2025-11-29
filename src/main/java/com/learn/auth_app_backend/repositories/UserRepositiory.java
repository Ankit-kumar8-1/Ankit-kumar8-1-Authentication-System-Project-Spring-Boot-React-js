package com.learn.auth_app_backend.repositories;

import com.learn.auth_app_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositiory extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    boolean exitsByEmail(String email);
}
