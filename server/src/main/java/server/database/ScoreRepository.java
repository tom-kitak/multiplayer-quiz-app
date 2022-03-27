package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import server.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {}
