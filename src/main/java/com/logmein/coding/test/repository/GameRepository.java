package com.logmein.coding.test.repository;

import com.logmein.coding.test.entity.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game,Long> {
}
