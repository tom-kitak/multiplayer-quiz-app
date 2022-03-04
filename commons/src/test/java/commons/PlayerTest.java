package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PlayerTest {
    Player player1;
    Player player2;
    @BeforeEach
    public void setUp(){
        player1 = new Player("Ja");
        player2 = new Player("Ja");
    }

    @Test
    void testConstructor(){
        assertNotNull(player1);
    }

    @Test
    void getUsername() {
        assertEquals("Ja", player2.getUsername());
    }

    @Test
    void getScore() {
        assertEquals(0, player1.getScore());
    }

    @Test
    void setScore() {
        Player player3 = new Player("Jim");
        player3.setScore(55);
        assertEquals(55, player3.getScore());
    }

    @Test
    void testEquals() {
        assertEquals(player1, player2);
    }

    @Test
    void testInequality(){
        Player player3 = new Player("Jim");
        assertNotEquals(player1, player3);
    }

    @Test
    void testInequality2(){
        Player player3 = new Player("Ja");
        player3.setScore(1);
        assertNotEquals(player3, player1);
    }

    @Test
    void testInequality3(){
        String string = "l";
        assertNotEquals(player1, string);
    }

    @Test
    void testHashCode() {
        assertEquals(player1.hashCode(), player2.hashCode());
    }

    @Test
    void testDifferentHash(){
        Player player3 = new Player("Jim");
        assertNotEquals(player1.hashCode(), player3.hashCode());
    }

    @Test
    void testToString(){
        String test = player1.toString();
        assertTrue(test.contains("Player:")&&test.contains("username")&&test.contains("Ja")
                    && test.contains("score:") &&test.contains("0"));
    }

    @Test
    void testUpdateScore(){
        Player player3 = new Player("Jim");
        player3.setScore(55);
        player3.upDateScore(5);
        assertTrue(60 == player3.getScore());
    }
}