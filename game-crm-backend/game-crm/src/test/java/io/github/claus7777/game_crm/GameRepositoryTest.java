package io.github.claus7777.game_crm;

import java.util.Optional;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import io.github.claus7777.game_crm.Game;
import io.github.claus7777.game_crm.GameRepository;

@DataJpaTest
public class GameRepositoryTest {
    
@Autowired
private GameRepository gameRepository;

@Test
void shouldfindGameByName(){
    Game game = new Game();
    game.setName("Game1");
    gameRepository.save(game);

    Optional<Game> found = gameRepository.findByName("Game1");

    Assertions.assertThat(found).isPresent();
    Assertions.assertThat(found.get().getName()).isEqualTo("Game1");
}

@Test
void shouldFindGameByNameContaining(){
    Game game = new Game();
    game.setName("Fighting Street");
    gameRepository.save(game);

    Game newGame = new Game();
    newGame.setName("Deadly Kombat");
    gameRepository.save(newGame);

    List<Game> found = gameRepository.findByNameContainingIgnoreCase("street");

    Assertions.assertThat(found).isNotEmpty();
    Assertions.assertThat(found.get(0).getName()).isEqualTo("Fighting Street");
}

}
