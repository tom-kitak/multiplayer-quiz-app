package commons;

import java.util.Objects;

public class Emoji {

    private String type;

    /**
     * Constructor to define the type of the emoji.
     * @param type These include: happy, sad, angry, and shocked.
     */
    public Emoji(String type){
        this.type = type;
    }

    /**
     * Used for transferring the emoji.
     */
    public Emoji(){

    }

    /**
     * Getter for the emoji type.
     * @return this.type.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the emoji type.
     * @param type of the emoji you want, these are: happy, sad, angry, and shocked.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Equals method for this object.
     * @param o Object to be compared against.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Emoji)) return false;
        Emoji emoji = (Emoji) o;
        return Objects.equals(type, emoji.type);
    }

    /**
     * @return an integer that is the hashCode of this Emoji.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    /**
     * To String method for Emoji.
     * @return String representation of Emoji object.
     */
    @Override
    public String toString() {
        return "Emoji{" +
                "type='" + type + '\'' +
                '}';
    }
}
