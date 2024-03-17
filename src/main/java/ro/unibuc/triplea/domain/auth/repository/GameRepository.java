package ro.unibuc.triplea.domain.auth.repository;

import ro.unibuc.triplea.application.auth.dto.response.GameResponse;
import ro.unibuc.triplea.domain.auth.model.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Optional<GameResponse> findByGameSteamId(Integer gameSteamId);
    Optional<GameResponse> findByGameName(String gameName);
    List<GameResponse> findAll(Optional<Integer> count);

    Game save(Game game);
}
