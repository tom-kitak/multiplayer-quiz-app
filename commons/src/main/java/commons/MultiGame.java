package commons;

import java.util.ArrayList;
import java.util.Objects;

public class MultiGame extends Game{
    private ArrayList<Player> players;


    /**
     * Creates a new Game Object.
     *
     * @param currentQuestion the first Question we start the game with.
     */
    public MultiGame(Question currentQuestion) {
        super(currentQuestion);
        this.players = new ArrayList<>();
    }

    public MultiGame() {
        super();
    }

    /**Adds a player to the list of players.
     * @param player the player we want to add
     */
    public void addPlayer(Player player){
        players.add(player);
    }

    /**Removes a player from the list.
     * @param player the player we want to remove
     */
    public void removePlayer(Player player){
        players.remove(player);
    }

    /**
     * @return the list of Players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**Sets the list of Players.
     * @param players the new value we want to assign to players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**Checks this MultiGame and o for equality.
     * @param o the object we want to compare for equality with
     * @return true iff o is a MultiGame with equal fields
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiGame)) return false;
        if (!super.equals(o)) return false;
        MultiGame multiGame = (MultiGame) o;
        return Objects.equals(getPlayers(), multiGame.getPlayers());
    }

    /**
     * @return the hashCode of this Object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPlayers());
    }

    /**
     * @return a String representation of this Object.
     */
    @Override
    public String toString() {
        return "MultiGame: \nplayers: " + players.toString()
                +"\nquestionNumber: " + this.getQuestionNumber()
                +"\ncurrentQuestion" + this.getCurrentQuestion();
    }
}
