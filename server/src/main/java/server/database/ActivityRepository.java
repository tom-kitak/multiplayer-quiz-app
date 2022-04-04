package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import commons.Activity;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @Query(value = "SELECT * FROM activity ORDER BY random() LIMIT 4", nativeQuery = true)
    Collection<Activity> getRandomActivities();
}
