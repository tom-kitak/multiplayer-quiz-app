package server.api;

import commons.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.Activity;
import server.database.ActivityRepository;

import java.util.Arrays;
import java.util.Random;

import static server.util.QuestionConversion.convertActivity;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final Random random;
    private final ActivityRepository repo;

    public QuestionController(Random random, ActivityRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = {"", "/"})
    public ResponseEntity<Question> getQuestion() {
        if (repo.count() < 4)
            return ResponseEntity.internalServerError().build();
        Activity[] activities = new Activity[4];
        for (int i = 0; i < 4; i++) {
            var idx = random.nextLong(repo.count());
            Activity a = repo.findById(idx).get();
            if (!Arrays.stream(activities).toList().contains(a)) {
                activities[i] = a;
            } else {
                i--;
            }
        }
        Question question = convertActivity(activities);
        if (question == null)
            return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok(question);
    }
}
