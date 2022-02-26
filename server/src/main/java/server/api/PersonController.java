package server.api;

import java.util.List;
import java.util.Random;

import commons.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import server.database.PersonRepository;


@RestController
@RequestMapping("/api/person")
public class PersonController {
    private final Random random;
    private final PersonRepository repo;

    public PersonController(Random random, PersonRepository repo){
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = "getAll")
    public List<Person> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") long id){
        if(id < 0 || !repo.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @PostMapping(path =  "post")
    public ResponseEntity<Person> add(@RequestBody Person person){

        if(isNullorEmpty(person.firstName) || isNullorEmpty(person.lastName)){
            return ResponseEntity.badRequest().build();
        }

        Person saved = repo.save(person);
        return ResponseEntity.ok(saved);
    }

    private static boolean isNullorEmpty(String s){
        return s == null || s.isEmpty();
    }

    @PutMapping(path = "put")
    public ResponseEntity<Person> add(@RequestBody Person person, long id){
        if(id < 0|| isNullorEmpty(person.firstName) || isNullorEmpty(person.lastName)){
            return ResponseEntity.badRequest().build();
        }

        if(repo.existsById(id)){
            repo.findById(id).get().setFirstName(person.firstName);
            repo.findById(id).get().setLastName(person.lastName);
        }
        else{
            person.setId(id);
            repo.save(person);
        }
        return ResponseEntity.ok(person);

    }

    @DeleteMapping(path ="delete")
    public ResponseEntity delete(@RequestBody long id){
        if(id < 0){
            return ResponseEntity.badRequest().build();
        }

        if(!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("rnd")
    public ResponseEntity<Person>  getRandomPerson(){
        var idx = random.nextInt((int) repo.count());
        return ResponseEntity.ok(repo.findById((long) idx).get());
    }
}
