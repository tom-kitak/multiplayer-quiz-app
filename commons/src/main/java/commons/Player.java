package commons;

import java.util.Objects;

public class Player {

    private final String username;
    private int score;

    /**constructs a new Player object.
     * @param username the username of the new Player
     */
    public Player(String username) {
        this.username = username;
        this.score = 0;
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

    /** we set the score of this Player.
     * @param score the score we want to assign to this player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**checks if this Player and Object o are equal.
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
     * @return a hashcode for this Player
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, score);
    }

    /**
     * @return String representation of this Player
     */
    @Override
    public String toString(){
        String result = "Player: \nusername=" + this.username + "\nscore=" + this.score;
        return result;
    }
}
