package ro.unibuc.triplea.infrastructure.game.repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import ro.unibuc.triplea.application.auth.dto.response.GameResponse;
import ro.unibuc.triplea.domain.auth.model.entity.Game;
import ro.unibuc.triplea.domain.auth.repository.GameRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Primary
public class GameRepositoryImpl implements GameRepository {

    @Value("${STEAM_API_KEY}")
    private String steamApiKey;
    
    private final RestTemplate restTemplate;
    private final SpringDataGameRepository springDataGameRepository;

    public GameRepositoryImpl(RestTemplate restTemplate, SpringDataGameRepository springDataGameRepository) {
        this.restTemplate = restTemplate;
        this.springDataGameRepository = springDataGameRepository;
    }

    @Override
    public List<GameResponse> findAll(Optional<Integer> count) {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(apiUrl, JsonNode.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {

            JsonNode responseNode = responseEntity.getBody();

            List<GameResponse> games = new ArrayList<>();
            if (responseNode != null && responseNode.has("applist")) {
                JsonNode appList = responseNode.get("applist");
                if (appList.has("apps")) {
                    JsonNode apps = appList.get("apps");

                    int limit = count.orElse(Integer.MAX_VALUE);
                    int counter = 0;
                    for (JsonNode app : apps) {
                        if (counter >= limit) {
                            break;
                        }

                        if (app.get("name").asText() != "") {
                            GameResponse game = GameResponse.builder().gameSteamId(app.get("appid").asInt()).gameName(app.get("name").asText()).build();
                            games.add(game);

                            counter++;
                        }
                    }
                }
            }

            return games;
        } else {
            System.err.println("Failed to fetch games from the external API. Status code: " + responseEntity.getStatusCode());
            return Collections.emptyList();
        }
    }

    @Override
    public Game save(Game game) {
        return springDataGameRepository.save(game);
    }

    @Override
    public Optional<GameResponse> findByGameSteamId(Integer gameSteamId) {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(apiUrl, JsonNode.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JsonNode responseNode = responseEntity.getBody();

            if (responseNode != null && responseNode.has("applist")) {
                JsonNode appList = responseNode.get("applist");
                if (appList.has("apps")) {
                    JsonNode apps = appList.get("apps");
                    for (JsonNode app : apps) {
                        if (app.get("appid").asInt() == gameSteamId) {
                            GameResponse game = GameResponse.builder().gameSteamId(app.get("appid").asInt()).gameName(app.get("name").asText()).build();
                            return Optional.of(game);
                        }
                    }
                }
            }
        } else {
            System.err.println("Failed to fetch games from the external API. Status code: " + responseEntity.getStatusCode());
        }

        return Optional.empty();
    }

    @Override
    public Optional<GameResponse> findByGameName(String gameName) {
        String apiUrl = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?key=" + steamApiKey + "&format=json";
        ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(apiUrl, JsonNode.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JsonNode responseNode = responseEntity.getBody();

            if (responseNode != null && responseNode.has("applist")) {
                JsonNode appList = responseNode.get("applist");
                if (appList.has("apps")) {
                    JsonNode apps = appList.get("apps");
                    for (JsonNode app : apps) {
                        if (app.get("name").asText().equalsIgnoreCase(gameName)) {
                            GameResponse game = GameResponse.builder().gameSteamId(app.get("appid").asInt()).gameName(app.get("name").asText()).build();
                            return Optional.of(game);
                        }
                    }
                }
            }
        } else {
            System.err.println("Failed to fetch games from the external API. Status code: " + responseEntity.getStatusCode());
        }
        
        return Optional.empty();
    }

}
