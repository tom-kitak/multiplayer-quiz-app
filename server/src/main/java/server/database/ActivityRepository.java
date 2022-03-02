package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import server.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
