package com.k4zmow.cards.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.k4zmow.cards.controller.request.CardRequest;
import com.k4zmow.cards.controller.request.CardUpdateRequest;
import com.k4zmow.cards.exception.CardAlreadyExistException;
import com.k4zmow.cards.exception.CardException;
import com.k4zmow.cards.model.Card;
import com.k4zmow.cards.model.Status;
import com.k4zmow.cards.model.User;
import com.k4zmow.cards.repository.CardRepository;
import com.k4zmow.cards.util.CardMapping;
import com.k4zmow.cards.util.CardSpecification;
import com.k4zmow.cards.util.Roles;
import com.k4zmow.cards.util.SearchCriteria;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserService userService;

    public CardService(CardRepository cardRepository, UserService userService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
    }

    public Card create(CardRequest cardRequest, Principal principal) throws CardAlreadyExistException, CardException {
        User loggedUser = userService.getLoggedUser(principal);
        Card card = CardMapping.parseCardRequest(cardRequest)
                .withStatus(Status.TO_DO)
                .withUserId(loggedUser.getId());
        try {
            cardRepository.save(card);
        } catch (DataIntegrityViolationException e) {
            throw new CardAlreadyExistException("Card name already exists");
        } catch (Exception e) {
            throw new CardException("An error occurred trying to save the card");
        }
        return cardRepository.save(card);
    }

    public Optional<Card> getCard(String name, Principal principal) {
        User loggedUser = userService.getLoggedUser(principal);
        if (isAdmin(loggedUser)) {
            return cardRepository.findByName(name);
        } else {
            return cardRepository.findByNameAndUserId(name, loggedUser.getId());
        }
    }

    @Transactional
    public void deleteCard(String name, Principal principal) throws CardException {
        User loggedUser = userService.getLoggedUser(principal);
        Card card = cardRepository.findByName(name)
                .orElseThrow(() -> new CardException("Card not found"));

        if (isAdmin(loggedUser)) {
            cardRepository.delete(card);
        } else {
            if (Objects.equals(card.getUserId(), loggedUser.getId())) {
                cardRepository.deleteByNameAndUserId(name, loggedUser.getId());
            } else {
                throw new CardException("Access denied: deleting a card not owned");
            }
        }
    }

    public List<Card> getAllCard(Principal principal) {
        User loggedUser = userService.getLoggedUser(principal);
        if (isAdmin(loggedUser)) {
            return cardRepository.findAll();
        } else {
            return cardRepository.findAllCardByUserId(loggedUser.getId());
        }
    }

    public Page<Card> getCardsByQuery(Principal principal, String name, String color, LocalDate date, Pageable pageable) {
        User loggedUser = userService.getLoggedUser(principal);
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        if (name != null) {
            searchCriteriaList.add(new SearchCriteria("name", "EQ", name));
        }
        if (color != null) {
            searchCriteriaList.add(new SearchCriteria("color", "EQ", "#" + color));
        }
        if (date != null) {
            searchCriteriaList.add(new SearchCriteria("creationDate", "EQ", date));
        }
        if (!isAdmin(loggedUser)) {
            searchCriteriaList.add(new SearchCriteria("userId", "EQ", loggedUser.getId()));
        }
        CardSpecification cardSpecification =  new CardSpecification(searchCriteriaList);
        return cardRepository.findAll(cardSpecification, pageable);
    }

    private boolean isAdmin(User loggedUser) {
        return loggedUser.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase(Roles.ADMIN.getName()));
    }

    public Card  updateCard(String name, CardUpdateRequest cardRequest, Principal principal) throws CardException {
        User loggedUser = userService.getLoggedUser(principal);
        Card card = cardRepository.findByName(name)
                .orElseThrow(() -> new CardException("Card not found"));

        Card cardToUpdate = new Card();
        if (cardRequest.getName() != null) {
            cardToUpdate = card.withName(cardRequest.getName());
        }
        if (cardRequest.getDescription() != null) {
            cardToUpdate = card.withDescription(cardRequest.getDescription());
        }
        if (cardRequest.getColor() != null) {
            cardToUpdate = card.withColor(cardRequest.getColor());
        }
        if (cardRequest.getStatus() != null) {
            cardToUpdate = card.withStatus(Status.fromString(cardRequest.getStatus()));
        }

        if (isAdmin(loggedUser)) {
            return cardRepository.save(cardToUpdate);
        } else {
            if (Objects.equals(card.getUserId(), loggedUser.getId())) {
                return cardRepository.save(cardToUpdate);
            } else {
                throw new CardException("Access denied: updating a card not owned");
            }
        }
    }
}
