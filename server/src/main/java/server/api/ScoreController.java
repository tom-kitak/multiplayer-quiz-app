package server.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//CHECKSTYLE:OFF
import org.springframework.web.bind.annotation.*;
//CHECKSTYLE:ON
import commons.Score;
import server.database.ScoreRepository;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    public final Random random;
    public final ScoreRepository repo;

    /**
     * Creates a new ScoreController.
     * @param random a random object we assign to our random class attribute
     * @param repo the ScoreRepository we assign to our class attribute
     */
    public ScoreController(Random random, ScoreRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    /**
     * Gets all the Scores from the ScoreRepository.
     * @return a List of Scores containing all the scores
     * that were stored in the repository
     */
    @GetMapping(path = "get")
    public List<Score> getAll(){
        return repo.findAll();
    }


    /**
     * Gets a Score from the repository by the provided id.
     * @param id the integer representing the id of the Score object we are looking for
     * @return a Clienterror if the id was smaller than 0 or a Score what that id does not exist
     * or it will return the Score of that id from the repository
     */
    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Score> getById(@PathVariable("id") long id){
        if(id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(repo.findById(id).orElse(null));
    }

    /**
     * Gets the top 10 results from the database.
     * @return an array of Score objects
     */
    @GetMapping(path = "/top")
    public ResponseEntity<List<Score>> getRandom(){
        return ResponseEntity.ok(repo.getTopScores().stream().toList());
    }

    /**
     * Saves the  Score object that was sent by the client in the repository.
     * @param score the Score object that was sent by the client
     * @return a client error if the username of the Player is null or has no characters
     * or it will sent a succes message if the Score was successfully saved
     */
    @PostMapping(path = "/post")
    public ResponseEntity<Score> add(@RequestBody Score score){
        if(score.getName() == null || score.getName().length() == 0)
            return ResponseEntity.badRequest().build();
        Score saved = repo.save(score);
        return ResponseEntity.ok(saved);
    }

    /**
     * Will update the Score Object that is in the repository by the provided id.
     * @param newScore the newScore object we want to replace the other one with
     * @param id the integer that is used to find the outdated score object out the
     *           repository
     * @return the Score object that was sent by the client
     */
    @PutMapping(path = "/put/{id}")
    public Score updateById(@RequestBody Score newScore, @PathVariable("id") long id){
        return repo.findById(id).map(score -> {
            score.setScore(newScore.getScore());
            score.setName(newScore.getName());
            score.setId(newScore.getId());
            return repo.save(score);
        }).orElseGet(()->{
            newScore.setId(id);
            return repo.save(newScore);
        });
    }

    /**
     * Will delete the Score object with the given id from the repository.
     * @param id the id with which we can identify the Score we are asked to delete
     * @return a success message to the client
     */
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Score> deleteById(@PathVariable("id") long id){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
