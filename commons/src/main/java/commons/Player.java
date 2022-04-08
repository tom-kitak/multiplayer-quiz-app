package commons;

import java.util.Objects;

public class Player {

    private String username;
    private int score;

    /**constructs a new Player object.
     * @param username the username of the new Player
     */
    public Player(String username) {
        this.username = username;
        this.score = 0;
    }

    public Player() {
        //for object mappers
    }

    /**
     * @return the username of this Player
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the score of this Player
     */
    public int getScore() {
        return score;
    }

    /**
     * we set the score of this Player.
     * @param score the score we want to assign to this player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * checks if this Player and Object o are equal.
     * @param o the object we compare with
     * @return true iff Player and o are both instances of Player
     * and have the same score and username
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return score == player.score && Objects.equals(username, player.username);
    }

    /**
     * @return a hashcode for this Player.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, score);
    }

    /**
     * @return a String representation of this Player Object.
     */
    @Override
    public String toString(){
        return "Player:\nusername: " + this.username +"\nscore: "+ this.score;
    }


    /**
     * @param add the number of points to add
     */
    public void upDateScore(int add) {
        this.score = score + add;
    }
}
