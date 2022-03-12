package client.scenes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuizScreenCtrlTest {

    @Test
    void convertTimer() {
        assertEquals("00:00", QuizScreenCtrl.convertTimer(0));
        assertEquals("00:05", QuizScreenCtrl.convertTimer(5));
        assertEquals("00:15", QuizScreenCtrl.convertTimer(15));
        assertEquals("01:00", QuizScreenCtrl.convertTimer(60));
        assertEquals("12:34", QuizScreenCtrl.convertTimer(754));
    }
}