package ro.unibuc.triplea.application.favorites.steam.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.triplea.application.favorites.steam.dto.request.SteamGameFavoriteRequest;
import ro.unibuc.triplea.application.favorites.steam.dto.response.SteamGameFavoriteResponse;
import ro.unibuc.triplea.domain.favorites.steam.service.SteamGameFavoriteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/favorites/steam")
@RequiredArgsConstructor
@Tag(name = "SteamFavoriteGames", description = "Favorite Steam Game management APIs")

public class SteamGameFavoriteController {

    private final SteamGameFavoriteService steamGameFavoriteService;

    private static final Logger logger = LoggerFactory.getLogger(SteamGameFavoriteController.class);
    @GetMapping("/user-name/{userName}")
    public ResponseEntity<?> getFavoritesByUserId(@PathVariable String userName) {
        logger.info("Fetching favorites for user: {}", userName);
        Optional<List<SteamGameFavoriteResponse>> favorites = steamGameFavoriteService.getFavoritesByUserName(userName);
        if (favorites.isPresent()) {
            logger.info("Favorites found for user: {}", userName);
            return ResponseEntity.ok(favorites.get());
        } else {
            logger.warn("No favorites found for user: {}", userName);
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestBody SteamGameFavoriteRequest favorite, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        logger.info("Adding favorite for user: {}", username);
        Optional<SteamGameFavoriteResponse> addedFavorite = steamGameFavoriteService.addFavorite(favorite, userDetails);
        if (addedFavorite.isPresent()) {
            logger.info("Favorite added successfully for user: {}", username);
            return ResponseEntity.ok(addedFavorite.get());
        } else {
            logger.error("Failed to add favorite for user: {}", username);
            return ResponseEntity.badRequest().build();
        }
    }
}
