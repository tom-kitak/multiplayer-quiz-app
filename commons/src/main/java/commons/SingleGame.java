package commons;

import java.util.Objects;

public class SingleGame {

    private Player player;
    private int questionNumber;
    private Question currentQuestion;

    /**Constructs a new SingleGame Object.
     * @param player the Player we want to assign as Field
     * @param currentQuestion the Question we will display as the first question
     */
    public SingleGame(Player player, Question currentQuestion) {
        this.player = player;
        this.questionNumber = 1;
        this.currentQuestion = currentQuestion;
    }

    /**
     * @return the player of this SingleGame
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the questionNumber of this SingleGame
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

    /**
     * @return the current Question of this SingleGame
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**Sets the questionNumber of this SingleGame.
     * @param questionNumber the number we will assign to questionNumber
     */
    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**Sets the currentQuestion of this SingleGame.
     * @param currentQuestion the new currentQuestion we will assign
     */
    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    /**Updates the score of the Player of the SingleGame.
     * @param add the number of Points to add
     */
    public void upDateScore(int add){
        this.player.upDateScore(add);
    }

    /**Cheks if two objects are equal.
     * @param o the object we will compare this Game with
     * @return true iff o is an instance of SingleGame and has an equal player
     * questionNumber and currentQuestion
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleGame that = (SingleGame) o;
        return questionNumber == that.questionNumber && Objects.equals(player, that.player)
                && Objects.equals(currentQuestion, that.currentQuestion);
    }

    /**
     * @return an integer that represents the hashCode of this SingleGame
     */
    @Override
    public int hashCode() {
        return Objects.hash(player, questionNumber, currentQuestion);
    }

    /**
     * @return a String representation of this SingleGame
     */
    @Override
    public String toString() {
        return "SingleGame: \n" + this.player.toString() +"\nquestionNumber: " + this.questionNumber
                +"\n" + this.currentQuestion.toString();
    }
}
