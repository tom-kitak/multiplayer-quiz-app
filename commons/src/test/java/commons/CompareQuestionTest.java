package commons;


import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CompareQuestionTest {



    @Test
    void testConstructor(){
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                new long[]{2, 2, 2, 2}, null);
        assertNotNull(question);
    }
    @Test
    void testConstructorError_1(){
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(null,
                    new long[]{2, 2, 2, 2}, null);
        });
    }

    @Test
    void testConstructorError_2(){
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                    null, null);
        });
    }
    @Test
    void testConstructorError_3(){
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x"},
                    new long[]{2, 2, 2, 2}, null);
        });
    }
    @Test
    void testConstructorError_4(){
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                    new long[]{2, 2, 2}, null);
        });
    }

    @Test
    void testGetAnswerTitles() {
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        assertTrue(Arrays.equals(new String[]{"x", "x", "x", "x"}, question.getAnswerTitles()));
    }

    @Test
    void testGetAnswerWattages() {
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        assertTrue(Arrays.equals(new long[]{2, 2, 2, 2}, question.getAnswerWattages()));
    }

    @Test
    void testGetCorrectAnswer() {
        CompareQuestion question = new CompareQuestion(new String[]{"x", "a", "a", "a"}, new long[]{2, 3, 3, 3}, null);
        assertEquals("x", question.getCorrectAnswer());
    }

    @Test
    void testGetCorrectWattage() {
        CompareQuestion question = new CompareQuestion(new String[]{"x", "a", "a", "a"}, new long[]{2, 3, 3, 3}, null);
        assertEquals(2, question.getCorrectWattage());
    }

    @Test
    void testEquals() {
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        CompareQuestion a = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        assertTrue(q.equals(a));
    }

    @Test
    void testInequality_1(){
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        CompareQuestion a = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{3, 3, 3, 3}, null);
        assertFalse(a.equals(q));
    }

    @Test
    void testInequality_2(){
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        CompareQuestion a = new CompareQuestion(new String[]{"a", "a", "a", "a"}, new long[]{2, 2, 2, 2}, null);
        assertFalse(a.equals(q));
    }


    @Test
    void testHashCode(){
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        CompareQuestion a = new CompareQuestion(new String[]{"a", "a", "a", "a"}, new long[]{3, 3, 3, 3}, null);
        assertNotEquals(a.hashCode(), q.hashCode());
    }

    @Test
    void testHashCode2(){
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        CompareQuestion a = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        assertEquals(q.hashCode(), a.hashCode());
    }

    @Test
    void testToString(){
        String s = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null).toString();
        System.out.println(s);
    }

    @Test
    void testGetDescription(){
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, null);
        assertEquals("Which of the following activities "
        +"uses the least amount of wh?", question.getQuestionDescription());
    }


}