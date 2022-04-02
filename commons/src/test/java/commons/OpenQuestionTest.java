package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenQuestionTest {

    long[] wattages = {1, 2, 3, 4};
    String[] descr = {"a", "b", "c", "d"};
    OpenQuestion openQuestion;
    byte[] test_image;

    @BeforeEach
    void setUp(){
        System.out.println("loading tests!");
        try {
            test_image = new FileInputStream("src/test/resources/images/test_image.jpg").readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openQuestion = new OpenQuestion(descr, wattages, test_image);

    }

    @Test
    void getRightAnswer() {
        assertEquals(1, openQuestion.getRightAnswer());
    }

    @Test
    void testEquals() {
        OpenQuestion openQuestion2 = new OpenQuestion(descr, wattages, test_image);
        assertEquals(openQuestion2, openQuestion);
    }

    @Test
    void testEquals2(){
        assertEquals(openQuestion, openQuestion);
    }
    @Test
    void testInEquality1(){
        long[] diff = {1, 2, 3, 5};
        OpenQuestion openQuestion1 = new OpenQuestion(descr, diff, test_image);
        assertNotEquals(openQuestion1, openQuestion);
    }

    @Test
    void testInEquality2(){
        String[] diff = {"a", "b", "d", "e"};
        OpenQuestion openQuestion1 = new OpenQuestion(diff, wattages, test_image);
        assertNotEquals(openQuestion1, openQuestion);
    }

    @Test
    void getQuestionDescription() {
        String test = "How many watt hours does a consume?";
        assertEquals(test, openQuestion.getQuestionDescription());
    }

    @Test
    void testToString() {
        String result = openQuestion.toString();
        assertTrue(result.contains("OpenQuestion")&&
                result.contains("answerTitles")&&result.contains("answerWattages"));
    }

    @Test
    void testHashCode() {
        OpenQuestion openQuestion1 = new OpenQuestion(descr, wattages, test_image);
        assertEquals(openQuestion.hashCode(), openQuestion1.hashCode());
    }

    @Test
    void testDifferentHashCodes(){
        String[] diff = {"a", "b", "d", "e"};
        OpenQuestion openQuestion1 = new OpenQuestion(diff, wattages, test_image);
        assertNotEquals(openQuestion1.hashCode(), openQuestion.hashCode());
    }

    @Test
    void testDifferentHashCodes2(){
        long[] diff = {1, 2, 3, 5};
        OpenQuestion openQuestion1 = new OpenQuestion(descr, diff, test_image);
        assertNotEquals(openQuestion1.hashCode(), openQuestion.hashCode());
    }




}