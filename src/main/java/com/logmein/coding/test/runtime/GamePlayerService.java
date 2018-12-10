package com.logmein.coding.test.runtime;

import com.logmein.coding.test.entity.Card;
import com.logmein.coding.test.entity.Game;
import com.logmein.coding.test.entity.Player;
import com.logmein.coding.test.exception.ResourceNotFoundException;
import com.logmein.coding.test.repository.GameRepository;
import com.logmein.coding.test.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.*;


@Service
public class GamePlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameDeckService gameDeckService;

    Map<Player,List<Card>> playersMap = new HashMap<>();

    public void registerPlayer(Player player)
    {
        playersMap.put(player,new ArrayList<>());
    }

    @PutMapping(value ="/games/{gameId}/players/dealCards")
    public Set<Player> dealCardToPlayers(Long gameId )
    {
        Game game= gameRepository.findById(gameId).get();
        playersMap.forEach((m,l)->{
                            m.setCardsAssigned(l);
                            m.addDealCardToList(gameDeckService.dealCardFromGameDeck(game));
                            playersMap.replace(m,m.getCardsAssigned());
        });
       return playersMap.keySet();
    }

    public Optional<Player> dealCardToOnePlayer(Game game ,Player player)
    {
        Player playerCached = playersMap.keySet().stream().filter(e->e.getPlayerId().longValue()== player.getPlayerId().longValue()).findFirst().
               orElseThrow(()->new ResourceNotFoundException("Player not found"));
      playerCached.addDealCardToList(gameDeckService.dealCardFromGameDeck(game));
      playersMap.replace(playerCached,playerCached.getCardsAssigned());
      return Optional.of(playerCached);
    }

   public List<Card> getListOfCardsOfPlayer(Player player){
       return playersMap.get(player);
    }

    public Map<Player,Integer> getTotalCardsValueOfAllPlayers()
    {
        Map<Player,Integer> map = new TreeMap<>();
        playersMap.forEach((p,c)->{
            map.put(p, p.getTotalCardValue());
        });
        return map;
    }
}
