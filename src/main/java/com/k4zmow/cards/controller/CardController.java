package com.k4zmow.cards.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.k4zmow.cards.controller.request.CardRequest;
import com.k4zmow.cards.controller.request.CardUpdateRequest;
import com.k4zmow.cards.exception.CardAlreadyExistException;
import com.k4zmow.cards.exception.CardException;
import com.k4zmow.cards.model.Card;
import com.k4zmow.cards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/cards")
public class CardController {

    private final static String HEX_COLOR_PATTERN = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Creates a card, name is mandatory")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Card> createCard(@RequestBody CardRequest cardRequest, Principal principal) throws CardAlreadyExistException, CardException {
        if (cardRequest == null || cardRequest.getName() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (cardRequest.getColor() != null && !cardRequest.getColor().matches(HEX_COLOR_PATTERN)) {
            throw new CardException("Color must be an hex color code");
        }

        return new ResponseEntity<>(cardService.create(cardRequest, principal), HttpStatus.OK);
    }

    @Operation(summary = "Get all cards, depending on user role. If admin returns everything")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Card>> getAllCards(Principal principal) throws CardException {
        return new ResponseEntity<>(cardService.getAllCard(principal), HttpStatus.OK);
    }

    @Operation(summary = "Get a card by its name, that the authenticated user owns")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Card> getCardByName(@Parameter(description = "name of the card to be searched") @PathVariable String name,
                                              Principal principal) {
        Optional<Card> card = cardService.getCard(name, principal);
        return card.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Delete a card by its name, that the authenticated user owns")
    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<Card> deleteCardByName(@Parameter(description = "name of the card to be deleted") @PathVariable String name,
                                                 Principal principal)
            throws CardException {
        cardService.deleteCard(name, principal);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Operation(summary = "Updates a card, that the authenticated user owns")
    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public ResponseEntity<Card> updateCard(@Parameter(description = "name of the card to be updated") @PathVariable String name,
                                           @RequestBody CardUpdateRequest cardRequest,
                                           Principal principal) throws CardException {
        Card card = cardService.updateCard(name, cardRequest, principal);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @Operation(summary = "Search cards by custom filter, that the authenticated user owns")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String color,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creationDate,
                                    Pageable pageable,
                                    Principal principal) {
        return new ResponseEntity<>(cardService.getCardsByQuery(principal, name, color, creationDate, pageable), HttpStatus.OK);
    }

}
