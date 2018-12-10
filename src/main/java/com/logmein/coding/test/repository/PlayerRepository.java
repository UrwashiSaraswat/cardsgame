package com.logmein.coding.test.repository;

import com.logmein.coding.test.entity.Game;
import com.logmein.coding.test.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> {

   List<Player> findByGame(@Param("gameId") Game gameId);
}
