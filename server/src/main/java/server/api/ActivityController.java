package server.api;

import org.springframework.http.ResponseEntity;
import commons.Activity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    private final ActivityRepository repo;

    /**
     * Creates a new ActivityController and assigns the repository.
     * @param repo the repository we assign to the repo attribute
     */
    public ActivityController(ActivityRepository repo) {
        this.repo = repo;
    }

    /**
     * Responds to the get method and getting all the activities.
     * @return Returns all objects stored in the database.
     */
    @GetMapping(path = {"", "/", "/getAll"})
    public List<Activity> getAll() {
        if(repo.count() > 0) {
            return repo.findAll();
        }else {
            return new ArrayList<Activity>();
        }
    }

    /**
     * checks if the server is alive and if there are enough activities in the repository.
     * @return always true
     */
    @GetMapping("/check")
    public Boolean returnCheck() {
        return true;
    }


    /**
     * checks whether there are enough activities.
     * @return whether there are enough activities
     */
    @GetMapping("/check/activity")
    public Boolean returnSufficientActivities() {
        return repo.count() >= 4;}


    /**
     * Will get an activity by the specified ID that was a path variable.
     * @param id The identifier of the object to be retrieved
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
     * Saves the activity that was sent by the client in the repository.
     * @param activity The activity to be added.
     * @return The activity that was added.
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Activity> add(@RequestBody Activity activity) {

        // Activities with id 0 can't get deleted for some reason (Also id 0 does not get assigned).
        if(activity == null || activity.getTitle() == null) {
            System.err.println(activity);
            return ResponseEntity.badRequest().build();
        }

        Activity saved = repo.save(activity);
        return ResponseEntity.ok(saved);
    }

    /**
     * Will delete the activity that was sent from the client from the repository.
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
