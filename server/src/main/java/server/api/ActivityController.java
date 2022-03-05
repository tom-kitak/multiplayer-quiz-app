package server.api;

import org.springframework.http.ResponseEntity;
//CHECKSTYLE:OFF
import org.springframework.web.bind.annotation.*;
//CHECKSTYLE:ON
import server.Activity;
import server.database.ActivityRepository;

import java.util.List;


@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    private final ActivityRepository repo;

    public ActivityController(ActivityRepository repo) {
        this.repo = repo;
    }

    /**
     * @return Returns all objects stored in the database.
     */
    @GetMapping(path = {"", "/"})
    public List<Activity> getAll() {
        return repo.findAll();
    }

    /**
     * @param id The identifier of the object to be retrieved.
     * @return Returns only the specified object
     */
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getById(@PathVariable("id") long id) {
        if(id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(repo.findById(id).orElse(null));
    }

    /**
     * @param activity The activity to be added.
     * @return The activity that was added.
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Activity> add(@RequestBody Activity activity) {

        // Activities with id 0 can't get deleted for some reason.
        if(activity.getSource() == null || activity.getTitle() == null || activity.id == 0) {
            return ResponseEntity.badRequest().build();
        }

        Activity saved = repo.save(activity);
        return ResponseEntity.ok(saved);
    }

    /**
     * @param id The identifier of the object to be deleted
     * @return The deleted object
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Activity> delete(@PathVariable("id") long id) {
        if(id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        Activity deleted = repo.findById(id).orElse(null);
        repo.deleteById(id);
        return ResponseEntity.ok(deleted);
    }
}
