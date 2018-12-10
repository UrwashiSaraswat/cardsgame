package com.logmein.coding.test.controller;

import com.logmein.coding.test.entity.Deck;
import com.logmein.coding.test.exception.ResourceNotFoundException;
import com.logmein.coding.test.repository.DeckRepository;
import com.logmein.coding.test.repository.GameRepository;
import com.logmein.coding.test.runtime.GameDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class DeckController {

    @Autowired
    GameDeckService gameDeckService;

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    GameRepository gameRepository;

    @PostMapping(value = "games/{gameId}/deck")
    public Resource<Deck> createDeck(@PathVariable Long gameId, @Valid @RequestBody Deck deck) {
        return gameRepository.findById(gameId).map(game -> {
            deck.setGame(game);
            deck.createInitializeAndRegisterDeck(game);
            gameDeckService.registerDeckWithGame(deck);
            return new Resource<>(deckRepository.save(deck));
        }).orElseThrow(() -> new ResourceNotFoundException("GameId " + gameId + " not found"));
    }
}
