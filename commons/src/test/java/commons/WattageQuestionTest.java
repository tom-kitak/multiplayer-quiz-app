package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WattageQuestionTest {
    WattageQuestion question1;
    WattageQuestion question2;

    @BeforeEach
    void setUp() {
        String[] answerTitles = {"a", "b", "c", "d"};
        int[] wattages = {1, 2, 3, 4};
        question1 = new WattageQuestion(answerTitles, wattages);
        question2 = new WattageQuestion(answerTitles, wattages);
    }

    @Test
    void testConstructor() {
        assertNotNull(question1);
    }

    @Test
    void testConstructorError1() {
        assertThrows(IllegalArgumentException.class, () -> {
            WattageQuestion question = new WattageQuestion(null, null);
        });
    }

    @Test
    void testConstructorError2() {
        assertThrows(IllegalArgumentException.class, () -> {
            WattageQuestion question = new WattageQuestion(new String[]{"ff", "gd", "ffd", "ljk"},
                    null);
        });
    }

    @Test
    void testConstructorError3() {
        assertThrows(IllegalArgumentException.class, () -> {
            WattageQuestion question = new WattageQuestion(null,
                    new int[]{55, 66, 88, 99});
        });
    }

    @Test
    void testConstructorError_4() {
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"a", "b", "c", "d", "d"},
                    new int[]{2, 2, 2, 2});
        });
    }

    @Test
    void testConstructorError_5() {
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                    new int[]{544, 44, 55});
        });
    }

    @Test
    void testGetAnswerTitles(){
        String[] test = {"a", "b", "c", "d"};
        assertTrue(Arrays.equals(test, question2.getAnswerTitles()));
    }

    @Test
    void testGetAnswerWattages(){
        int[] test = {1, 2, 3, 4};
        assertTrue(Arrays.equals(test, question2.getAnswerWattages()));
    }

    @Test
    void testGetRightAnswer(){
        assertEquals(1, question1.getRightAnswer());
    }

    @Test
    void testGetCorrectAnswer(){
        assertEquals("a", question1.getCorrectAnswer());
    }

    @Test
    void testGetCorrectWattage() {
        assertEquals(1, question1.getCorrectWattage());
    }

    @Test
    void testequals(){
        assertEquals(question1, question2);
    }

    @Test
    void testInequality1(){
        WattageQuestion question = new WattageQuestion(new String[]
                {"a", "a", "c", "d"}, new int[] {1, 2, 3, 4});
        assertNotEquals(question, question1);
    }

    @Test
    void testInequality2(){
        WattageQuestion question = new WattageQuestion(new String[]
                {"a", "b", "c", "d"}, new int[] {1, 1, 3, 4});
        assertNotEquals(question, question1);
    }

    @Test
    void testInequality(){
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                new int[]{2, 2, 2, 2});
        assertNotEquals(question, question1);
    }

    @Test
    void testHashCode(){
        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    void testDifferentHashCodes(){
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                new int[]{2, 2, 2, 2});
        assertNotEquals(question.hashCode(), question1.hashCode());
    }

    @Test
    void testToString(){
        String result = question1.toString();
        assertTrue(result.contains("answerTitles")
                && result.contains("answerWattages"));
    }

    @Test
    void testGetQuestionDescription(){
        assertEquals("a", question1.getQuestionDescription());
    }






}
