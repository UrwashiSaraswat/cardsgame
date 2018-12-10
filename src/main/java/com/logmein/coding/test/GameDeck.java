package com.logmein.coding.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.logmein.coding.test.entity.Game;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "GameDeck")
public class GameDeck implements Serializable {

    private static final long serialVersionUID = 1355734366208893923L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonProperty
    @Column(name ="GameDeck_Id")
    private Long gameDeckId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GAME_Id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    public GameDeck() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameDeck)) return false;
        GameDeck gameDeck = (GameDeck) o;
        return Objects.equal(gameDeckId, gameDeck.gameDeckId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gameDeckId);
    }

    @Override
    public String toString() {
        return "GameDeck{" +
                "gameDeckId=" + gameDeckId +
                '}';
    }
}
