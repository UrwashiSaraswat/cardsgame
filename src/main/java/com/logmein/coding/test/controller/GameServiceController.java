package com.logmein.coding.test.controller;

import com.logmein.coding.test.entity.Game;
import com.logmein.coding.test.entity.Player;
import com.logmein.coding.test.exception.ResourceNotFoundException;
import com.logmein.coding.test.repository.DeckRepository;
import com.logmein.coding.test.repository.GameRepository;
import com.logmein.coding.test.repository.PlayerRepository;
import com.logmein.coding.test.runtime.GameDeckService;
import com.logmein.coding.test.runtime.GamePlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(path = "/", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class GameServiceController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameDeckService gameDeckService;

    @Autowired
    GamePlayerService gamePlayerService;

    @PostMapping(value = "/game")
    public Resource<Game> createGame(@RequestBody Game game)
    {
        Optional.ofNullable(game).orElseThrow(()->new IllegalArgumentException(" Json object for game is null "));
        return new Resource<>(gameRepository.save(game));
    }

    @DeleteMapping(value = "/game/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id){

        gameRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/games/{gameId}/players/{playerId}")
    public Resource<Player> dealCardForPlayer(@PathVariable Long gameId, @PathVariable Long playerId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Game does not exist"));
        return playerRepository.findById(playerId).map(player -> {
                  return new Resource<>(gamePlayerService.dealCardToOnePlayer(game,player).orElseThrow(()->new IllegalArgumentException("Exception when dealing card")));
        }).orElseThrow(() -> new ResourceNotFoundException("PlayerId " + playerId + " not found"));

    }
    @PutMapping(value ="/games/{gameId}/players/dealCards")
    public Set<Player> dealCardToAllPlayers(@PathVariable Long gameId )
    {
        if(!gameRepository.existsById(gameId))
                throw new ResourceNotFoundException("Game " + gameId + " not found");
       return gamePlayerService.dealCardToPlayers(gameId);
    }

     @PutMapping("/games/{gameId}/shuffle")
     public void shuffleGameDeck()
     {
        gameDeckService.shuffle();
     }

    @GetMapping("/games/{gameId}/players/{playerId}/totalCards")
     public Map<Player,Integer> getPlayersAndTotalCardValue(@PathVariable Long gameId)
     {
         if(!gameRepository.existsById(gameId))
             throw new ResourceNotFoundException("Game " + gameId + " not found");
         return gamePlayerService.getTotalCardsValueOfAllPlayers();

     }

}
