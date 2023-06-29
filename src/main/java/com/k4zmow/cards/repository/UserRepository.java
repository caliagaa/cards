package com.k4zmow.cards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k4zmow.cards.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
