package commons;

import java.util.Objects;

public class SingleGame extends Game {

    private Player player;

    /**
     * Constructs a new SingleGame Object.
     * @param player the Player we want to assign as Field
     * @param currentQuestion the Question we will display as the first question
     */
    public SingleGame(Player player, Question currentQuestion) {
        super(currentQuestion);
        this.player = player;
    }

    /**
     * @return the player of this SingleGame
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Updates the score of the Player of the SingleGame.
     * @param add the number of Points to add
     */
    public void upDateScore(int add){
        this.player.upDateScore(add);
    }

    /**
     * Checks o and this Singlegame object for equality.
     * @param o the object we want to compare for equality with
     * @return true iff o is a SngleGame object and has equal fields
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleGame)) return false;
        if (!super.equals(o)) return false;
        SingleGame game = (SingleGame) o;
        return Objects.equals(getPlayer(), game.getPlayer());
    }

    /**
     * @return hashCode of this Object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPlayer());
    }

    /**
     * @return a String representation of this SingleGame.
     */
    @Override
    public String toString() {
        return "SingleGame: \n" + this.player.toString() +"\nquestionNumber: " + this.getQuestionNumber()
                +"\n" + this.getCurrentQuestion().toString();
    }


}
