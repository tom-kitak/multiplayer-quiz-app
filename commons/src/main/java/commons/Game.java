package commons;

import java.util.Objects;

public abstract class Game {

    private int questionNumber;
    private Question currentQuestion;


    /**
     * Creates a new Game Object.
     * @param currentQuestion the first Question we start the game with.
     */
    public Game(Question currentQuestion) {
        this.questionNumber = 1;
        this.currentQuestion = currentQuestion;
    }

    public Game() {
    }

    /**
     * Updates the questionNumber and currentQuestion of this SingleGame.
     * @param question the new currentQuestion
     */
    public void nextQuestion(Question question){
        this.currentQuestion = question;
        this.questionNumber++;
    }

    /**
     * @return the number of the question we are on.
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

    /**
     * Sets the questionNumber.
     * @param questionNumber the int we want the questionNumber to be.
     */
    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**
     * @return the question the players are currently answering.
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Sets the currentQuestion.
     * @param currentQuestion the question we want to assign.
     */
    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    /**
     * Checks this Game and o for equality.
     * @param o the object we want to compare for equality with
     * @return true iff o is a Game and it has equal the same fields as this Game
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return questionNumber == game.questionNumber && Objects.equals(currentQuestion, game.currentQuestion);
    }

    /**
     * @return an integer that is the hashCode of this Game.
     */
    @Override
    public int hashCode() {
        return Objects.hash(questionNumber, currentQuestion);
    }

    /**
     * @return a String representation of this Game object.
     */
    @Override
    public String toString() {
        return "Game{" +
                "questionNumber=" + questionNumber +
                ", currentQuestion=" + currentQuestion +
                '}';
    }
}
