package ro.unibuc.triplea.domain.auth.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.triplea.domain.auth.repository.GameRepository;
import ro.unibuc.triplea.application.auth.dto.response.GameResponse;

@Service
public class GameService {
    
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameResponse> getAllGames(Optional<Integer> count) {
        return gameRepository.findAll(count);
    }

    public Optional<GameResponse> getGameBySteamId(Integer gameSteamId) {
        return gameRepository.findByGameSteamId(gameSteamId);
    }

    public Optional<GameResponse> getGameByName(String gameName) {
        return gameRepository.findByGameName(gameName);
    }
}
