package server.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//CHECKSTYLE:OFF
import org.springframework.web.bind.annotation.*;
//CHECKSTYLE:ON
import server.Score;
import server.database.ScoreRepository;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    public final Random random;
    public final ScoreRepository repo;

    public ScoreController(Random random, ScoreRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = "get")
    public List<Score> getAll(){
        return repo.findAll();
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Score> getById(@PathVariable("id") long id){
        if(id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(repo.findById(id).orElse(null));
    }

    @GetMapping(path = "/get/rnd")
    public ResponseEntity<Score> getRandom(){
        var indx = random.nextInt((int)repo.count());
        return ResponseEntity.ok(repo.findById((long) indx).orElse(null));
    }

    @PostMapping(path = "/post")
    public ResponseEntity<Score> add(@RequestBody Score score){
        if(score.getName() == null || score.getName().length() == 0)
            return ResponseEntity.badRequest().build();
        Score saved = repo.save(score);
        return ResponseEntity.ok(saved);
    }

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

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Score> deleteById(@PathVariable("id") long id){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
