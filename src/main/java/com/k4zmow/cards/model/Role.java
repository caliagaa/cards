package com.k4zmow.cards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @Column(name = "role_id")
    private Long id;
    private String name;
}
