package ro.unibuc.triplea.domain.games.steam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameNotFoundException;
import ro.unibuc.triplea.domain.games.steam.repository.SteamGameRepository;
import ro.unibuc.triplea.domain.games.steam.utils.IdentifierUtil;

import java.util.List;
import java.util.Optional;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Service
@RequiredArgsConstructor
public class SteamGameService {

    private final SteamGameRepository steamGameRepository;

    private final MeterRegistry meterRegistry;

    public List<SteamGameResponse> getAllGames(Optional<Integer> count) {
        Timer.Sample sample = Timer.start(meterRegistry);

        List<SteamGameResponse> games = steamGameRepository.findGames(count);

        sample.stop(meterRegistry.timer("getAllGames.time"));

        return games;

    }

    public Optional<SteamGameResponse> getGameBySteamId(int gameSteamId) {
        return steamGameRepository.findByGameSteamId(gameSteamId);
    }

    public Optional<SteamGameResponse> getGameByName(String gameName) {
        return steamGameRepository.findByGameName(gameName);
    }

    public Optional<SteamGameResponse> getGameByIdentifier(String identifier) {
        Optional<SteamGameResponse> game;
        if (IdentifierUtil.isPositiveNumeric(identifier)) {
            int steamId = Integer.parseInt(identifier);
            game = getGameBySteamId(steamId);
        } else {
            game = getGameByName(identifier);
        }
        if (game.isEmpty()) {
            throw new SteamGameNotFoundException("Steam game with identifier " + identifier + " not found");
        }
        return game;
    }
}
