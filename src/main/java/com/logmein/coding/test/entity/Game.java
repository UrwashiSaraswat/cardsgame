package com.logmein.coding.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Game")
public class Game implements Serializable
{
    private static final long serialVersionUID = -5469521149928779981L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonProperty
    @Column(name ="GAME_ID")
    private Long gameId;

    public Game() {
    }

    public Long getGameId() {
        return gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return Objects.equal(getGameId(), game.getGameId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getGameId());
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", decks=" + "decks" +
                ", players=" + "players" +
                '}';
    }

    public int assignCardsToPlayer(Player player)
    {
        return 0;
    }

}
