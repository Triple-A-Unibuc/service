package ro.unibuc.triplea.domain.games.steam.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.exception.SteamGameValidateException;
import ro.unibuc.triplea.domain.games.steam.repository.SteamGameRepository;

@Service
@RequiredArgsConstructor
public class SteamGameService {
    
    private final SteamGameRepository steamGameRepository;

    public List<SteamGameResponse> getAllGames(Optional<Integer> count) {
        return steamGameRepository.findGames(count);
    }

    public Optional<SteamGameResponse> getGameBySteamId(int gameSteamId) {
        return steamGameRepository.findByGameSteamId(gameSteamId);
    }

    public Optional<SteamGameResponse> getGameByName(String gameName) {
        return steamGameRepository.findByGameName(gameName);
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    
    private boolean isValidString(String str) {
        return Pattern.matches("^([^\\\\u4E00-\\\\u9FFF]*?)(?:[\\\\p{L}0-9' .:~\\\\u4E00-\\\\u9FFF].*)?$", str);
    }

    public Optional<SteamGameResponse> getGameByIdentifier(String identifier) {
        try {
            if (isNumeric(identifier)) {
                int steamId = Integer.parseInt(identifier);
                return getGameBySteamId(steamId);
            } else if (isValidString(identifier)) {
                return getGameByName(identifier);
            } else {
                throw new SteamGameValidateException("Invalid identifier: " + identifier);
            }
        } catch (Exception e) {
            throw new SteamGameValidateException("Invalid identifier: " + identifier);
        }
    }
}
