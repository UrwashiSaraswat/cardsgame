package com.logmein.coding.test.controller;

import com.logmein.coding.test.entity.Card;
import com.logmein.coding.test.entity.Player;
import com.logmein.coding.test.exception.ResourceNotFoundException;
import com.logmein.coding.test.repository.GameRepository;
import com.logmein.coding.test.repository.PlayerRepository;
import com.logmein.coding.test.runtime.GamePlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping(path = "/", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GamePlayerService gamePlayerService;

    @PostMapping(value = "games/{gameId}/players")
    public Resource<Player> createPlayer(@PathVariable Long gameId,@Valid @RequestBody  Player player) {
        return gameRepository.findById(gameId).map(game -> {
            player.setGame(game);
            gamePlayerService.registerPlayer(player);
            return new Resource<>(playerRepository.save(player));
        }).orElseThrow(() -> new ResourceNotFoundException("GameId " + gameId + " not found"));
    }

    @DeleteMapping(value = "games/{gameId}/players/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long gameId, @PathVariable Long playerId) {
        if (!gameRepository.existsById(gameId)) {
            throw new ResourceNotFoundException("GameId " + gameId + " not found");
        }
        return playerRepository.findById(playerId).map(player -> {
            playerRepository.delete(player);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PlayerId " + playerId + " not found"));
    }


    @PutMapping(value = "games/{gameId}/players/{playerId}")
    public Resource<Player> updatePlayer(@PathVariable Long gameId, @PathVariable Long playerId ,@Valid @RequestBody  Player playerJson) {
        if (!gameRepository.existsById(gameId)) {
            throw new ResourceNotFoundException("GameId " + gameId + " not found");
        }
        return playerRepository.findById(playerId).map(player -> {
            player.setPlayerName(playerJson.getPlayerName());
            player.setGame(playerJson.getGame());
            return new Resource<Player>(playerRepository.save(player));
        }).orElseThrow(() -> new ResourceNotFoundException("PlayerId " + playerId + " not found"));
    }

    @GetMapping("/games/{gameId}/players")
    public Resource<List<Player>> getAllPlayersByGameId(@PathVariable Long gameId ){
        return new Resource<>(playerRepository.findByGame(
                gameRepository.findById(gameId).
                        map(Function.identity()).
                        orElseThrow(() -> new ResourceNotFoundException("Game " + gameId + " not found"))
        ));
    }

    @GetMapping("/games/{gameId}/players/{playerId}/cards")
    public Resource<List<Card>> getListOfCardsOfPlayer(@PathVariable Long gameId , @PathVariable Long playerId )
    {
        if (!gameRepository.existsById(gameId)) {
            throw new ResourceNotFoundException("GameId " + gameId + " not found");
        }
        return new Resource<>(gamePlayerService.getListOfCardsOfPlayer(playerRepository.findById(playerId).
                    orElseThrow(()->new ResourceNotFoundException("Player does not exist with Id= "+playerId))));
    }

}
