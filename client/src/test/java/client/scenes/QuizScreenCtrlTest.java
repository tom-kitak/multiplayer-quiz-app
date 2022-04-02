package client.scenes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuizScreenCtrlTest {

    QuizScreenCtrl ctrl;

    @BeforeEach
    void setUp(){
        ctrl = new QuizScreenCtrl(null, null);
    }

    @Test
    void convertTimer() {
        assertEquals("00:00", ctrl.convertTimer(0, 0));
        assertEquals("00:05", ctrl.convertTimer(0, 5));
        assertEquals("01:00", ctrl.convertTimer(1, 0));
        assertEquals("12:34", ctrl.convertTimer(12, 34));
    }

    @Test
    void generateIndex(){
        int index = ctrl.generateIndex(44);
        assertTrue(index>=0&&index<=44);
    }
    @Test
    void generateIndex2(){
        int index = ctrl.generateIndex(0);
        assertTrue(index>=0&&index<=0);
    }

    @Test
    void testInRangeForOpenQuestion(){
        long correct = 1000;
        long answer = 501;
        assertTrue(ctrl.inRangeForOpenQuestion(answer, correct));
    }

    @Test
    void testInRangeForOpenQuestion2(){
        long correct = 5;
        long answer = 2;
        assertTrue(!ctrl.inRangeForOpenQuestion(answer, correct));
    }

    @Test
    void testInRangeForOpenQuestion3(){
        assertTrue(ctrl.inRangeForOpenQuestion(4000, 3500));
    }

    @Test
    void testCalculateScoreOpenQuestion(){
        long correct = 1000;
        long answer = 1400;
    }



}