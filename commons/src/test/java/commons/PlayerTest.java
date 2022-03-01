package commons;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlayerTest {

    @Test
    void testConstructor(){
        Player p = new Player("Jim");
        assertNotNull(p);
    }

    @Test
    void getUsername() {
        Player p = new Player("Jim");
        assertEquals("Jim", p.getUsername());
    }

    @Test
    void getScore() {
        Player p = new Player("Jim");
        assertEquals(0, p.getScore());
    }

    @Test
    void setScore() {
        Player p = new Player("Jim");
        p.setScore(56);
        assertEquals(56, p.getScore());
    }

    @Test
    void testEquals() {
        Player p = new Player("Jim");
        Player q = new Player("Jim");
        assertEquals(p, q);
    }

    @Test
    void testToString() {
        String result = "Player: \nusername=Jim\nscore=0";
        Player p = new Player("Jim");
        assertEquals(result, p.toString());
    }
}