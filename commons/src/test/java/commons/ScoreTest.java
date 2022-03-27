package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreTest {
    Score score1;

    @BeforeEach
    void setUp(){
        score1 = new Score(22, "a");
    }

    @Test
    void testConstructor(){
        assertNotNull(score1);
    }

    @Test
    void getId() {
        score1.setId(556);
        assertEquals(556, score1.getId());
    }

    @Test
    void setId() {
        score1.setId(66);
        assertEquals(66, score1.getId());
    }

    @Test
    void getScore() {
        assertEquals(22, score1.getScore());
    }

    @Test
    void setScore() {
        score1.setScore(44);
        assertEquals(44, score1.getScore());
    }

    @Test
    void getName() {
        assertEquals("a", score1.getName());
    }

    @Test
    void setName() {
        score1.setName("b");
        assertEquals("b", score1.getName());
    }

    @Test
    void testEquals() {
        assertEquals(score1, score1);
    }
    @Test
    void testEquals2(){
        Score score2 = new Score(22, "a");
        assertEquals(score2, score1);
    }
    @Test
    void testInequality(){
        Score score = new Score(21, "a");
        assertNotEquals(score, score1);
    }

    @Test
    void testInequality2(){
        Score score = new Score(22, "b");
        assertNotEquals(score, score1);
    }

    @Test
    void testInEquality3(){
        Score score = new Score(8699999, "dfgfd");
        assertNotEquals(score, score1);
    }

    @Test
    void testHashCode() {
        Score score = new Score(22, "a");
        assertEquals(score.hashCode(), score1.hashCode());
    }

    @Test
    void testDifferentHashCode(){
        Score score = new Score(55, "d");
        assertNotEquals(score.hashCode(), score1.hashCode());
    }

    @Test
    void testToString() {
        String result = score1.toString();
        assertTrue(result.contains("Score")&&result.contains("id")
                &&result.contains("score")&&result.contains("name"));
    }

}