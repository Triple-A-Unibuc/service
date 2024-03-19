package ro.unibuc.triplea.infrastructure.reviews.steam.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.unibuc.triplea.application.reviews.steam.dto.response.SteamGameReviewResponse;
import ro.unibuc.triplea.domain.reviews.steam.model.entity.SteamGameReview;
import ro.unibuc.triplea.domain.reviews.steam.repository.SteamGameReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@Primary
@RequiredArgsConstructor
public class SteamGameReviewRepositoryImpl implements SteamGameReviewRepository {

    private final SpringDataSteamGameReviewRepository springDataSteamGameReviewRepository;

    @Override
    public Optional<List<SteamGameReviewResponse>> findAllByGameSteamId(int gameSteamId) {
        List<SteamGameReview> steamGameReviews = springDataSteamGameReviewRepository.findAllByGameSteamId(gameSteamId);

        return steamGameReviews.stream()
                .map(steamGameReview -> SteamGameReviewResponse.builder()
                        .gameSteamId(steamGameReview.getGameSteamId())
                        .gameName(steamGameReview.getGameName())
                        .reviewContent(steamGameReview.getReviewContent())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SteamGameReviewResponse> findAllByGameName(String email, String gameName) {
        return Optional.empty();
    }

    @Override
    public SteamGameReview save(SteamGameReview game) {
        return null;
    }
}
