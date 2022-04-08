package server.api;

import commons.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import commons.Activity;
import server.database.ActivityRepository;


import java.util.Random;

import static server.util.QuestionConversion.convertActivity;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final Random random;
    private final ActivityRepository repo;

    /**
     * Creates a new QuestionController object.
     * @param random a new random object that we assign to our class attribute
     * @param repo the ActivityRepository that we assign to our class attribute
     */
    public QuestionController(Random random, ActivityRepository repo) {
        this.random = random;
        this.repo = repo;
    }



    /**
     * Will generate a new Question and send it to the client.
     * @return a new ResponseEntity containing the Question
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<Question> getQuestion() {
        // Can't create a question if there aren't enough activities
        if (repo.count() < 4)
            return ResponseEntity.internalServerError().build();

        Activity[] activities = repo.getRandomActivities().toArray(new Activity[4]);
        // Returns the result.
        Question question = convertActivity(activities);
        if (question == null)
            return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok(question);
    }
}
