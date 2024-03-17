package ro.unibuc.triplea.infrastructure.game.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ro.unibuc.triplea.domain.auth.model.entity.Game;
import ro.unibuc.triplea.domain.auth.repository.GameRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class GameRepositoryImpl implements GameRepository {

    @Value("${STEAM_API_KEY}")
    private String steamApiKey;
    
    private final RestTemplate restTemplate;
    private final SpringDataGameRepository springDataGameRepository;

    @Autowired
    public GameRepositoryImpl(RestTemplate restTemplate, SpringDataGameRepository springDataGameRepository) {
        this.restTemplate = restTemplate;
        this.springDataGameRepository = springDataGameRepository;
    }

    @Override
    public List<Game> findAll() {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<Game[]> response = restTemplate.getForEntity(apiUrl, Game[].class);
        
        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(response.getBody());
        } else {
            System.err.println("Failed to fetch games from the external API. Status code: " + response.getStatusCode());
            return Collections.emptyList();
        }
    }

    @Override
    public Game save(Game game) {
        return springDataGameRepository.save(game);
    }

    @Override
    public Optional<Game> findByGameSteamId(Integer gameSteamId) {
        // TODO modify here
        ResponseEntity<Game> response = restTemplate.getForEntity("http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=STEAMKEY&format=json" + gameSteamId, Game.class);
        
        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(response.getBody());
        } else {
            System.err.println("Failed to fetch game from the external API. Status code: " + response.getStatusCode());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Game> findByGameName(String gameName) {
        //TODO modify here
        ResponseEntity<Game> response = restTemplate.getForEntity("http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=STEAMKEY&format=json" + gameName, Game.class);
        
        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(response.getBody());
        } else {
            System.err.println("Failed to fetch game from the external API. Status code: " + response.getStatusCode());
            return Optional.empty();
        }
    }
}
