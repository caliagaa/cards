package com.k4zmow.cards.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.k4zmow.cards.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
