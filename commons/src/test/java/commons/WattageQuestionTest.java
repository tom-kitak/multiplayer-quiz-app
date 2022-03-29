package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WattageQuestionTest {
    WattageQuestion question1;
    WattageQuestion question2;
    byte[] test_image;

    public WattageQuestionTest() {
        System.out.println("loading tests!");
        try {
            test_image = new FileInputStream("src/test/resources/images/test_image.jpg").readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeEach
    void setUp() {
        String[] answerTitles = {"a", "b", "c", "d"};
        long[] wattages = {1, 2, 3, 4};
        question1 = new WattageQuestion(answerTitles, wattages, test_image);
        question2 = new WattageQuestion(answerTitles, wattages, test_image);
        try {
            test_image = new FileInputStream("resources/images/test_image.jpg").readAllBytes();
            System.out.println("image loaded!");
        } catch (IOException e) {
            System.out.println("image load failed!");
            e.printStackTrace();
        }
    }

    @Test
    void testConstructor() {
        assertNotNull(question1);
    }

    @Test
    void testConstructorError1() {
        assertThrows(IllegalArgumentException.class, () -> {
            WattageQuestion question = new WattageQuestion(null, null, test_image);
        });
    }

    @Test
    void testConstructorError2() {
        assertThrows(IllegalArgumentException.class, () -> {
            WattageQuestion question = new WattageQuestion(new String[]{"ff", "gd", "ffd", "ljk"},
                    null, test_image);
        });
    }

    @Test
    void testConstructorError3() {
        assertThrows(IllegalArgumentException.class, () -> {
            WattageQuestion question = new WattageQuestion(null,
                    new long[]{55, 66, 88, 99}, test_image);
        });
    }

    @Test
    void testConstructorError_4() {
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"a", "b", "c", "d", "d"},
                    new long[]{2, 2, 2, 2}, test_image);
        });
    }

    @Test
    void testConstructorError_5() {
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                    new long[]{544, 44, 55}, test_image);
        });
    }

    @Test
    void testGetAnswerTitles(){
        String[] test = {"a", "b", "c", "d"};
        assertArrayEquals(test, question2.getAnswerTitles());
    }

    @Test
    void testGetAnswerWattages(){
        long[] test = {1, 2, 3, 4};
        assertArrayEquals(test, question2.getAnswerWattages());
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
    void testEquals(){
        assertEquals(question1, question2);
    }

    @Test
    void testInequality1(){
        WattageQuestion question = new WattageQuestion(new String[]
                {"a", "a", "c", "d"}, new long[] {1, 2, 3, 4}, test_image);
        assertNotEquals(question, question1);
    }

    @Test
    void testInequality2(){
        WattageQuestion question = new WattageQuestion(new String[]
                {"a", "b", "c", "d"}, new long[] {1, 1, 3, 4}, test_image);
        assertNotEquals(question, question1);
    }

    @Test
    void testInequality(){
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                new long[]{2, 2, 2, 2}, test_image);
        assertNotEquals(question, question1);
    }

    @Test
    void testHashCode(){
        assertEquals(question1.hashCode(), question2.hashCode());
    }

    @Test
    void testDifferentHashCodes(){
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                new long[]{2, 2, 2, 2}, test_image);
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
        assertEquals("How many watt hours does a consume?", question1.getQuestionDescription());
    }






}
