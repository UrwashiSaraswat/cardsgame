package com.logmein.coding.test.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "Deck")
public class Deck implements Serializable {

    private static final long serialVersionUID = -8461631287500610776L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonProperty
    private Long deckId;

    @JsonProperty
    private transient int cardsInUse ;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GAME_Id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    private transient Card[] cards = new Card[]{};

    public Deck() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Deck(Card[] cards, int cardsInUse) {
        this.cards = cards;
        this.cardsInUse = cardsInUse;
    }

    public Long getDeckId() {
        return deckId;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public void setCardsInUse(int cardsInUse) {
        this.cardsInUse = cardsInUse;
    }

    public void shuffle() {
        for (int i = cardsInUse-1; i >=0; i--) {
            int randomIndex = (int)(Math.random()*(i+1));
            Card temp = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = temp;
        }
    }

    public void createInitializeAndRegisterDeck(Game game) {
        Set<Card> cardSet = new HashSet<>();
        for (CARDTYPE cardtype : CARDTYPE.values()) {
            for (int i = 1; i <= 13; i++) {
                cardSet.add(new Card(cardtype,CARDNUMBER.getNumberOfCard(i)));
            }
        }
        cards = cardSet.toArray(cards);
        cardsInUse = 0;
   }

    public Optional<Card> dealCard() {
        if (cardsInUse == 52) {
            return Optional.empty();
        }else{
            return Optional.of(cards[++cardsInUse - 1]);
        }
    }

    public int cardsLeftInDeck()
    {
        return 52 - cardsInUse;
    }
}
