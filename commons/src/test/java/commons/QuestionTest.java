package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestionTest {
    Question question1;
    Question question2;

    @BeforeEach
    void setUp(){
        question1 = new Question("desc", 55);
        question2 = new Question("desc", 55);
    }

    @Test
    void testConstructor(){
        assertNotNull(question1);
    }
    @Test
    void assignWrongAnswers() {
        ArrayList<Integer> wrongAnswers = question1.getWrongAnswers();
        assertTrue(wrongAnswers.size() == 3 && !wrongAnswers.contains(55));
    }

    @Test
    void getQuestion() {
        assertEquals("desc", question1.getQuestion());
    }

    @Test
    void getRightAnswer() {
        assertEquals(55, question1.getRightAnswer());
    }

    @Test
    void getWrongAnswers() {
        assertTrue(question1.getWrongAnswers().size() == 3);
    }

    @Test
    void testEquals() {
        assertEquals(question1, question2);
    }

    @Test
    void testInequality(){
        assertNotEquals(question1, new Question("asc", 55));
    }
    @Test
    void testInequality2() {
        assertNotEquals(question1, new Question("desc", 44));
    }

    @Test
    void testHashCode() {
        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    void testDifferentHashCode(){
        assertNotEquals(question1.hashCode(), new Question("desc", 11).hashCode());
    }

    @Test
    void testToString() {
        String result = question1.toString();
        System.out.println(question1.toString());
        assertTrue(result.contains("Question:") && result.contains("question=desc")
                    && result.contains("rightAnswer=55") && result.contains("wrongAnswers"));
    }
}
