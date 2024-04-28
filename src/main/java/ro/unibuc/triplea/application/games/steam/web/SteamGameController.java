package ro.unibuc.triplea.application.games.steam.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.triplea.application.games.steam.dto.response.SteamGameResponse;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/games/steam")
@RequiredArgsConstructor
@Tag(name = "SteamGames", description = "Steam Game management APIs")
public class SteamGameController {
    private static final Logger logger = LoggerFactory.getLogger(SteamGameController.class);

    private final SteamGameService steamGameService;

    @GetMapping("/game-list")
    public ResponseEntity<List<SteamGameResponse>> getAllGames(
            @RequestParam(required = false, name = "count") Optional<Integer> count) {
        logger.info("Fetching all games from Steam");
        List<SteamGameResponse> games = steamGameService.getAllGames(count);
        logger.info("Retrieved {} games from Steam", games.size());
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<?> getGameByIdentifier(@PathVariable String identifier) {
        logger.info("Fetching game by identifier: {}", identifier);
        Optional<SteamGameResponse> game = steamGameService.getGameByIdentifier(identifier);

        if (game.isPresent()) {
            logger.info("Game found for identifier: {}", identifier);
            return ResponseEntity.ok(game.get());
        } else {
            logger.warn("Game not found for identifier: {}", identifier);
            return ResponseEntity.notFound().build();
        }
    }
}
