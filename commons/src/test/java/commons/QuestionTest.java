package commons;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class QuestionTest {



    @Test
    void testConstructor(){
        Question question = new Question("?", 55);
        assertNotNull(question);
    }

    @Test
    void assignWrongAnswers() {
        Question question = new Question("?", 55);
        assertTrue(question.getWrongAnswers().size() == 3);

    }
    @Test
    void assignWrongAnswers2(){
        Question question = new Question("?", 55);
        ArrayList<Integer> integers = question.getWrongAnswers();
        assertTrue(integers.get(0) != integers.get(1) && integers.get(1) != integers.get(2)
                && integers.get(0)!= integers.get(2) && integers.get(0) != 55);
    }

    @Test
    void getQuestion() {
        Question q = new Question("get", 30);
        assertTrue(q.getQuestion().equals("get"));
    }

    @Test
    void getRightAnswer() {
        Question q = new Question("?", 33);
        assertTrue(33 == q.getRightAnswer());
    }

    @Test
    void getWrongAnswers() {
        Question q = new Question(">", 888);
        assertTrue(q.getWrongAnswers().size() == 3);
    }

    @Test
    void testEquals() {
        Question q = new Question(">", 69);
        Question a = new Question(">", 69);
        assertTrue(q.equals(a));
    }

    @Test
    void testInequality(){
        Question q = new Question("/", 45);
        Question a = new Question("/", 44);
        assertNotEquals(a, q);
    }

    @Test
    void testInequality2(){
        Question q = new Question(">", 4);
        Question a = new Question("7", 4);
        assertNotEquals(a, q);
    }

    @Test
    void testHashCode(){
        Question q = new Question(">", 44);
        Question a = new Question(">", 43);
        assertNotEquals(a.hashCode(), q.hashCode());
    }

    @Test
    void testHashCode2(){
        Question q = new Question(">", 44);
        Question p = new Question(">", 44);
        assertTrue(q.hashCode() == p.hashCode());
    }

    @Test
    void testToString(){
        String s = new Question("j", 5).toString();
        assertTrue(s.contains("j"));
        assertTrue(s.contains("5"));
    }






}