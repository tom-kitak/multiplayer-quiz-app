package server.api;

import commons.Emoji;
import commons.MultiGame;
import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MultiPlayerControllerTest {

    MultiPlayerController ctrl;

    @MockBean
    ActivityRepository repo;

    @MockBean
    MockRandom random;

    @BeforeEach
    void setUp() {
        ctrl = new MultiPlayerController(new Random(), repo);
    }

    @Test
    @DisplayName("Checks whether the /app/multi/ returns game when single player in waiting room")
    void connectOneTest() {
        Player player1 = new Player("Joe");
        MultiGame expected = new MultiGame(null);
        ArrayList<Player> expectedList = new ArrayList<>();
        expectedList.add(player1);
        expected.setPlayers(expectedList);
        assertEquals(expected, ctrl.connect(player1));
    }

    @Test
    @DisplayName("Checks whether the /app/multi/ returns game when two players in waiting room")
    void connectTwoTest() {
        Player player1 = new Player("Joe");
        Player player2 = new Player("Donald");
        MultiGame expected = new MultiGame(null);
        ArrayList<Player> expectedList = new ArrayList<>();
        expectedList.add(player1);
        expectedList.add(player2);
        expected.setPlayers(expectedList);
        ctrl.connect(player1);
        MultiGame game2 = ctrl.connect(player2);
        assertEquals(expected, game2);
    }

    @Test
    @DisplayName("Checks whether the /app/multi/ returns game when two players connect one disconnect in waiting room")
    void connectTwoAndOneDisconnectTest() {
        Player player1 = new Player("Joe");
        Player player2 = new Player("Donald");
        MultiGame expected = new MultiGame(null);
        ArrayList<Player> expectedList = new ArrayList<>();
        expectedList.add(player2);
        expected.setPlayers(expectedList);
        ctrl.connect(player1);
        ctrl.connect(player2);
        assertEquals(expected, ctrl.connect(player1));
    }

    @Test
    @DisplayName("Correct emoji returned")
    void emojiHandlerTest() {
        Emoji expected = new Emoji("happy");
        assertEquals(expected, ctrl.emojiHandler("0","happy", new Emoji("happy")));
    }

    @Test
    @DisplayName("Incorrect emoji returned")
    void emojiHandlerNotCorrectTest() {
        Emoji expected = new Emoji("happy");
        assertNotEquals(expected, ctrl.emojiHandler("0","happy", new Emoji("sad")));
    }
}