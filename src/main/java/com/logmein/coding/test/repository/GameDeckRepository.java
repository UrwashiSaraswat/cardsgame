package com.logmein.coding.test.repository;

import com.logmein.coding.test.GameDeck;
import com.logmein.coding.test.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface GameDeckRepository extends JpaRepository<GameDeck,Long> {

    Game findByGame(@Param("gameId") Game gameId);
}
