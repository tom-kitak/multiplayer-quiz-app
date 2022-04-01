package commons;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CompareQuestionTest {

    byte[] test_image;

    @BeforeEach
    void setUp() {
        System.out.println("loading tests!");
        try {
            test_image = new FileInputStream("src/test/resources/images/test_image.jpg").readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConstructor(){
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                new long[]{2, 2, 2, 2}, test_image);
        assertNotNull(question);
    }
    @Test
    void testConstructorError_1(){
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(null,
                    new long[]{2, 2, 2, 2}, test_image);
        });
    }

    @Test
    void testConstructorError_2(){
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                    null, test_image);
        });
    }
    @Test
    void testConstructorError_3(){
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x"},
                    new long[]{2, 2, 2, 2}, test_image);
        });
    }
    @Test
    void testConstructorError_4(){
        assertThrows(IllegalArgumentException.class, () -> {
            CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"},
                    new long[]{2, 2, 2}, test_image);
        });
    }

    @Test
    void testGetAnswerTitles() {
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        assertArrayEquals(new String[]{"x", "x", "x", "x"}, question.getAnswerTitles());
    }

    @Test
    void testGetAnswerWattages() {
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        assertArrayEquals(new long[]{2, 2, 2, 2}, question.getAnswerWattages());
    }

    @Test
    void testGetCorrectAnswer() {
        CompareQuestion question = new CompareQuestion(new String[]{"x", "a", "a", "a"}, new long[]{2, 3, 3, 3}, test_image);
        assertEquals("x", question.getCorrectAnswer());
    }

    @Test
    void testGetCorrectWattage() {
        CompareQuestion question = new CompareQuestion(new String[]{"x", "a", "a", "a"}, new long[]{2, 3, 3, 3}, test_image);
        assertEquals(2, question.getCorrectWattage());
    }

    @Test
    void testEquals() {
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        CompareQuestion a = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        assertEquals(q, a);
    }

    @Test
    void testInequality_1(){
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        CompareQuestion a = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{3, 3, 3, 3}, test_image);
        assertNotEquals(a, q);
    }

    @Test
    void testInequality_2(){
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        CompareQuestion a = new CompareQuestion(new String[]{"a", "a", "a", "a"}, new long[]{2, 2, 2, 2}, test_image);
        assertNotEquals(a, q);
    }


    @Test
    void testHashCode(){
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        CompareQuestion a = new CompareQuestion(new String[]{"a", "a", "a", "a"}, new long[]{3, 3, 3, 3}, test_image);
        assertNotEquals(a.hashCode(), q.hashCode());
    }

    @Test
    void testHashCode2(){
        CompareQuestion q = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        CompareQuestion a = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        assertEquals(q.hashCode(), a.hashCode());
    }

    @Test
    void testToString(){
        String s = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image).toString();
        System.out.println(s);
    }

    @Test
    void testGetDescription(){
        CompareQuestion question = new CompareQuestion(new String[]{"x", "x", "x", "x"}, new long[]{2, 2, 2, 2}, test_image);
        assertEquals("Which of the following activities "
        +"uses the least amount of wh?", question.getQuestionDescription());
    }


}