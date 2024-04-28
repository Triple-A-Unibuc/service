package ro.unibuc.triplea.application.reviews.steam.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.triplea.application.reviews.steam.dto.request.SteamGameReviewRequest;
import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.reviews.steam.service.SteamGameReviewService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews/steam")
@RequiredArgsConstructor
@Tag(name = "SteamGameReviews", description = "Steam Game Review management APIs")
public class SteamGameReviewController {
    private static final Logger logger = LoggerFactory.getLogger(SteamGameReviewController.class);

    private final SteamGameReviewService steamGameReviewService;

    @GetMapping("/game-id/{gameId}")
    public ResponseEntity<?> getReviewsBySteamId(@PathVariable String gameId) {
        logger.info("Fetching reviews for game with ID: {}", gameId);
        Optional<List<SteamGameReviewResponse>> reviews = steamGameReviewService.getReviewsByGameIdentifier(gameId);
        if (reviews.isPresent()) {
            logger.info("Reviews found for game with ID: {}", gameId);
            return ResponseEntity.ok(reviews.get());
        } else {
            logger.warn("No reviews found for game with ID: {}", gameId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user-name/{userName}")
    public ResponseEntity<?> getReviewsByUserId(@PathVariable String userName) {
        logger.info("Fetching reviews for user: {}", userName);
        Optional<List<SteamGameReviewResponse>> reviews = steamGameReviewService.getReviewsByUserName(userName);
        if (reviews.isPresent()) {
            logger.info("Reviews found for user: {}", userName);
            return ResponseEntity.ok(reviews.get());
        } else {
            logger.warn("No reviews found for user: {}", userName);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody SteamGameReviewRequest review, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        logger.info("Adding review for user: {}", username);
        Optional<SteamGameReviewResponse> addedReview = steamGameReviewService.addReview(review, userDetails);
        if (addedReview.isPresent()) {
            logger.info("Review added successfully for user: {}", username);
            return ResponseEntity.ok(addedReview.get());
        } else {
            logger.error("Failed to add review for user: {}", username);
            return ResponseEntity.badRequest().build();
        }
    }

}
