package com.logmein.coding.test.runtime;

import com.logmein.coding.test.entity.Card;
import com.logmein.coding.test.entity.Deck;
import com.logmein.coding.test.entity.Game;
import com.logmein.coding.test.repository.DeckRepository;
import com.logmein.coding.test.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameDeckService {

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    GameRepository gameRepository;

    private Map<Deck,List<Card>> uniqueGameDeckCard = new HashMap<>();

    public void registerDeckWithGame(Deck deck)
    {
        uniqueGameDeckCard.put(deck,Arrays.stream(deck.getCards()).collect(Collectors.toList()));
    }
    public Optional<Card> dealCardFromGameDeck(Game game) {
        Card[] array ={};
        for (Map.Entry<Deck, List<Card>> e : uniqueGameDeckCard.entrySet()) {
            e.getKey().setCards(e.getValue().toArray(array));
            Deck deck = e.getKey();
            deck.shuffle();
            Optional<Card> card = deck.dealCard();
            if(card.isPresent()) {
                updateDeckMap(deck);
                return card;
            }
        }
        return Optional.empty();
    }

    private void updateDeckMap(Deck deck)
    {
        uniqueGameDeckCard.replace(deck,Arrays.stream(deck.getCards()).collect(Collectors.toList()));
    }

    public void shuffle(){
        uniqueGameDeckCard.forEach((e,l)->{e.shuffle();updateDeckMap(e);});
    }

}
