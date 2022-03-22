package client.scenes;

import commons.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HomeScreenCtrlTest {
    HomeScreenCtrl ctrl;

    @BeforeEach
    void setUp(){
        this.ctrl = new HomeScreenCtrl(null, null);
    }

    @Test
    void getNewScore() {
        Score score = ctrl.getNewScore(55, "Jimmy");
        assertEquals(new Score(55, "Jimmy"), score);

    }

    @Test
    void generateRandomString() {
        String test = ctrl.generateRandomString();
        assertTrue(test.length() == 13);
    }

    @Test
    void generateIndex() {
        int test = ctrl.generateIndex(5);
        assertTrue(test>=0 && test<=5);
    }

    @Test
    void generateIndex2(){
        int test = ctrl.generateIndex(0);
        assertEquals(0, test);
    }
}