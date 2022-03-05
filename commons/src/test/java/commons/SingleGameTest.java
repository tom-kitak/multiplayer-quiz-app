package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SingleGameTest {
    SingleGame game1;
    SingleGame game2;
    Player player;
    Question question;

    @BeforeEach
    void setUp(){
        player = new Player("Jim");
        question = new Question("desc", 55);
        game1 = new SingleGame(player, question);
        game2 = new SingleGame(player, question);
    }

    @Test
    void testConstructor(){
        assertNotNull(game1);
    }

    @Test
    void getPlayer() {
        Player player1 = new Player("Jim");
        assertEquals(player1, game1.getPlayer());
    }

    @Test
    void getQuestionNumber() {
        assertEquals(1, game1.getQuestionNumber());
    }

    @Test
    void getCurrentQuestion() {
        assertEquals(question, game1.getCurrentQuestion());
    }

    @Test
    void setQuestionNumber() {
        game1.setQuestionNumber(2);
        assertEquals(2, game1.getQuestionNumber());
    }

    @Test
    void setCurrentQuestion() {
        Question question1 = new Question("asc", 87);
        game1.setCurrentQuestion(question1);
        assertEquals(question1, game1.getCurrentQuestion());
    }

    @Test
    void upDateScore() {
        game1.upDateScore(55);
        assertEquals(55, game1.getPlayer().getScore());
    }

    @Test
    void testEquals(){
        assertEquals(game1, game2);
    }

    @Test
    void testInequality(){
        Player player2 = new Player("ja");
        SingleGame game3 = new SingleGame(player2, question);
        assertNotEquals(game1, game3);
    }

    @Test
    void testInequality2(){
        Question question2 = new Question("gogo", 123456);
        game1.setCurrentQuestion(question2);
        assertNotEquals(game1, game2);
    }

    @Test
    void testHashCode(){
        assertEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    void testHashCode2(){
        game1.setCurrentQuestion(new Question("Ht", 98));
        assertNotEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    void testToString(){
        String result = game1.toString();
        assertTrue(result.contains("SingleGame")&&result.contains(player.toString())
                    &&result.contains(question.toString())&&result.contains("questionNumber"));
    }

    @Test
    void testNextQuestion(){
        Question question2 = new Question("new", 1200);
        game1.nextQuestion(question2);
        assertTrue(game1.getQuestionNumber() == 2 && game1.getCurrentQuestion().equals(question2));
    }
}