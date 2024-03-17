package ro.unibuc.triplea.infrastructure.game.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.unibuc.triplea.domain.auth.model.entity.Game;

public interface SpringDataGameRepository extends JpaRepository<Game, Integer> {
}
