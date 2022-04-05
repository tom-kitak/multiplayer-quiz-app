package server.database;

import commons.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query(value = "SELECT * FROM score ORDER BY score DESC LIMIT 10", nativeQuery = true)
    Collection<Score> getTopScores();
}
