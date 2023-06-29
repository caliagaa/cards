package com.k4zmow.cards.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.k4zmow.cards.util.StatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cards")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String color;

    @Convert(converter = StatusConverter.class)
    private Status status;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @JsonIgnore
    @Column(name = "user_id")
    private Long userId;

    public Card withDescription(String description) {
        this.description =  description;
        return this;
    }

    public Card withColor(String color) {
        this.color =  color;
        return this;
    }

    public Card withStatus(Status status) {
        this.status =  status;
        return this;
    }

    public Card withUserId(Long id) {
        this.userId =  id;
        return this;
    }

    public Card withName(String name) {
        this.name =  name;
        return this;
    }
}
