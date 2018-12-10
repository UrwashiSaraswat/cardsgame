package com.logmein.coding.test.repository;

import com.logmein.coding.test.GameDeck;
import com.logmein.coding.test.entity.Deck;
import com.logmein.coding.test.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck,Long> {
    List<Deck> findByGame(@Param("gameId") Game gameId);
}
