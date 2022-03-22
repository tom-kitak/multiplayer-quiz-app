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
        assertEquals("00:00", ctrl.convertTimer(0));
        assertEquals("00:05", ctrl.convertTimer(5));
        assertEquals("00:15", ctrl.convertTimer(15));
        assertEquals("01:00", ctrl.convertTimer(60));
        assertEquals("12:34", ctrl.convertTimer(754));
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



}