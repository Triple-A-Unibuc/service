package ro.unibuc.triplea.domain.auth.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "Games")
@Data
@EqualsAndHashCode(callSuper = false)
public class Game extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "game_steam_id", nullable = false)
    private Integer gameSteamId;

    @Column(name = "game_name", nullable = true)
    private String gameName;

    public Integer getId() {
        return id;
    }

    public Integer getGameSteamId() {
        return gameSteamId;
    }

    public void setGameSteamId(Integer gameSteamId) {
        this.gameSteamId = gameSteamId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "id=" + id +
                ", gameSteamId=" + gameSteamId +
                ", gameName='" + gameName + '\'' +
                '}';
    }
}
