package ro.unibuc.triplea.domain.reviews.steam.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import ro.unibuc.triplea.TripleaApplication;
import ro.unibuc.triplea.domain.games.steam.repository.SteamGameRepository;
import ro.unibuc.triplea.domain.games.steam.service.SteamGameService;
import ro.unibuc.triplea.domain.reviews.steam.repository.SteamGameReviewRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("IT")
public class SteamGameReviewServiceIT {

    @Test
    public void sampleTest() {
        // Given
        // When
        // Then
        assertEquals(true, true);
    }
}
