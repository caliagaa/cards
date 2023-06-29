package com.k4zmow.cards.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.k4zmow.cards.model.Card;

@Repository
public interface CardRepository extends CrudRepository<Card, Long>, JpaSpecificationExecutor<Card> {

    Optional<Card> findByName(String name);
    List<Card> findAll();

    @Query(value = "SELECT * FROM cards where user_id = ?1", nativeQuery = true)
    List<Card> findAllCardByUserId(Long id);

    @Query(value = "SELECT * FROM cards where name = ?1 and user_id = ?2", nativeQuery = true)
    Optional<Card> findByNameAndUserId(String name, Long id);

    @Modifying
    @Query(value = "DELETE FROM cards where name = ?1 and user_id = ?2", nativeQuery = true)
    void deleteByNameAndUserId(String name, Long id);
}
