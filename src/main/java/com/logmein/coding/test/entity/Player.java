package com.logmein.coding.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Entity (name = "Player")
public class Player implements Serializable , Comparable
{
    private static final long serialVersionUID = 5434503640729087181L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonProperty
    private Long playerId;

    @Column(name ="playername")
    @JsonProperty
    private String playerName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GAME_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    @JsonProperty
    private transient List<Card> cardsAssigned = new ArrayList<>();

    @JsonProperty
    private transient int totalCardValue=0;

    public Player() {
    }

    public Player(String playerName, Game game) {
        this.playerName = playerName;
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<Card> getCardsAssigned() {
        return cardsAssigned;
    }

    public void setCardsAssigned(List<Card> cardsAssigned) {
        this.cardsAssigned = cardsAssigned;
    }

    public boolean addDealCardToList(Optional<Card> card)
    {
        return card.isPresent()&& cardsAssigned.add(card.get());
    }

    public int getTotalCardValue(){
        if(cardsAssigned!=null) {
            totalCardValue = cardsAssigned.stream().mapToInt(c -> c.getCardNumber().getNumber()).reduce(0, Integer::sum);
        }
        return totalCardValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equal(getPlayerId(), player.getPlayerId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPlayerId());
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", cardsAssigned=" + cardsAssigned +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.getTotalCardValue()-((Player)o).getTotalCardValue();
    }
}
