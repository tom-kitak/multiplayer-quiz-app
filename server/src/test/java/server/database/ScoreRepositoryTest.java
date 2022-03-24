package server.database;

import commons.Score;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ScoreRepositoryTest {
    @Autowired
    ScoreRepository scoreRepository;
    Score score1;
    Score score2;

    @BeforeEach
    void setUp(){
        score1 = new Score(1, "a");
        score2 = new Score(2, "b");
        scoreRepository.save(score1);
    }

    @AfterEach
    void tearDown(){
        scoreRepository.deleteAll();
    }

    @Test
    void testSave(){
        List<Score> scoreList = scoreRepository.findAll();
        assertEquals(score1, scoreList.get(0));
    }

    @Test
    void findAll(){
        scoreRepository.save(score2);
        List<Score> scores = scoreRepository.findAll();
        assertEquals(score2, scores.get(1));
    }

    @Test
    void findById(){
        Optional<Score> score = scoreRepository.findById(score1.id);
        assertTrue(score.isPresent());

    }

    @Test
    void testFindById2(){
        Optional<Score> score = scoreRepository.findById(score2.id);
        assertTrue(score.isEmpty());
    }

    @Test
    void testExistsById(){
        assertTrue(scoreRepository.existsById(score1.id));
    }

    @Test
    void testExistsById2(){
        assertFalse(scoreRepository.existsById(score2.id));
    }

    @Test
    void testDeleteById(){
        scoreRepository.deleteById(score1.id);
        Optional<Score> score = scoreRepository.findById(score1.id);
        assertTrue(score.isEmpty());
    }

}