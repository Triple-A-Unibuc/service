package ro.unibuc.triplea.application.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameResponse {
    private Integer gameSteamId;
    private String gameName;
}
