package io.github.claus7777.game_crm;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByName(String name);

    List<Game> findByNameContainingIgnoreCase(String namePart);
}
