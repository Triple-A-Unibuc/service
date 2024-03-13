package ro.unibuc.triplea.application.auth.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.triplea.domain.auth.model.entity.Game;
import ro.unibuc.triplea.domain.auth.service.GameService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
@Tag(name = "Game", description = "Game management APIs")
public class GameController {
    
    private final GameService gameService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<?> getGameByIdentifier(@PathVariable String identifier) {
        
        Optional<Game> game;

        try {
            Integer steamId = Integer.parseInt(identifier);
            game = gameService.getGameBySteamId(steamId);
        } catch (NumberFormatException e) {
            game = gameService.getGameByName(identifier);
        }

        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
