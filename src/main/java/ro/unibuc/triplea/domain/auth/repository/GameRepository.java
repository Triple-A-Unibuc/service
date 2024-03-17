package ro.unibuc.triplea.domain.auth.repository;

import ro.unibuc.triplea.domain.auth.model.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Optional<Game> findByGameSteamId(Integer gameSteamId);
    Optional<Game> findByGameName(String gameName);
    List<Game> findAll(Optional<Integer> count);

    Game save(Game game);
}
