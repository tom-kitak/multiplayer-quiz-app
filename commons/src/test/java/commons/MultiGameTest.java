package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultiGameTest {

    private MultiGame game;
    private ArrayList<Player> players;
    private Player player;
    private Question question;
    byte[] test_image;

    @BeforeEach
    void setUp(){
        System.out.println("loading tests!");
        try {
            test_image = new FileInputStream("src/test/resources/images/test_image.jpg").readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player("j");
        players = new ArrayList<>();
        players.add(player);
        long[] wattages = new long[4];
        wattages[0] = 1;
        wattages[1] = 2;
        wattages[2] = 3;
        wattages[3] = 4;
        String[] descriptions = new String[4];
        descriptions[0] = "a";
        descriptions[1] = "b";
        descriptions[2]= "c";
        descriptions[3] = "d";
        question = new CompareQuestion(descriptions, wattages, test_image);
        this.game = new MultiGame(question);
    }

    @Test
    void testConstructor(){
        assertNotNull(game);
    }

    @Test
    void addPlayer() {
        game.addPlayer(player);
        assertEquals(players, game.getPlayers());
    }

    @Test
    void removePlayer() {
        game.addPlayer(player);
        game.removePlayer(player);
        ArrayList<Player> test = new ArrayList<>();
        assertEquals(test, game.getPlayers());
    }

    @Test
    void getPlayers() {
        ArrayList<Player> test = new ArrayList<>();
        assertEquals(test, game.getPlayers());
    }

    @Test
    void setPlayers() {
        game.setPlayers(players);
        assertEquals(players, game.getPlayers());
    }

    @Test
    void testEquals() {
        MultiGame game2 = new MultiGame(question);
        assertEquals(game, game2);
    }

    @Test
    void testInequality() {
        MultiGame game2 = new MultiGame(question);
        game2.addPlayer(player);
        assertNotEquals(game, game2);
    }

    @Test
    void testInequality2(){
        long[] wattages = new long[4];
        wattages[0] = 1;
        wattages[1] = 2;
        wattages[2] = 3;
        wattages[3] = 4;
        String[] descriptions = new String[4];
        descriptions[0] = "a";
        descriptions[1] = "b";
        descriptions[2]= "c";
        descriptions[3] = "d";
        Question question2 = new WattageQuestion(descriptions, wattages, test_image);
        MultiGame game2 = new MultiGame(question2);
        assertNotEquals(game2, game);
    }

    @Test
    void testInequality3(){
        MultiGame game2 = new MultiGame(question);
        game2.setId(55);
        assertNotEquals(game, game2);
    }

    @Test
    void testHashCode() {
        MultiGame game2 = new MultiGame(question);
        assertEquals(game.hashCode(), game2.hashCode());
    }

    @Test
    void testDifferentHashCodes(){
        long[] wattages = new long[4];
        wattages[0] = 1;
        wattages[1] = 2;
        wattages[2] = 3;
        wattages[3] = 4;
        String[] descriptions = new String[4];
        descriptions[0] = "a";
        descriptions[1] = "b";
        descriptions[2]= "c";
        descriptions[3] = "e";
        Question question2 = new WattageQuestion(descriptions, wattages, test_image);
        MultiGame game2 = new MultiGame(question2);
        assertNotEquals(game2.hashCode(), game.hashCode());
    }

    @Test
    void testToString() {
        String result = game.toString();
        assertTrue(result.contains("MultiGame: ") && result.contains("players: ")
                &&result.contains("questionNumber: ") && result.contains("currentQuestion: ")
                &&result.contains("id: "));
    }

    @Test
    void testGetId(){
        assertEquals(0, game.getId());
    }

    @Test
    void testSetId(){
        game.setId(5);
        assertEquals(5, game.getId());
    }
}