package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public int score;
    public String name;

    public Score() {
    }

    /**
     * Creates a new Score object.
     * @param score the integer we assign to the Score field
     * @param name the String we assign to the Name field
     */
    public Score(int score, String name){
        this.name = name;
        this.score = score;
    }

    /**
     * @return gets the id of this Score.
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id of this Score.
     * @param id the id we want to assign
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * @return the score of this Score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of this Score.
     * @param score the integer we want to set as new score
     */
    public void setScore(int score) {
        this.score = score;
    }


    /**
     * @return the name associated to this Score.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Score.
     * @param name the name we want to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Compares o and this Score for equality.
     * @param o the object we check with for equality
     * @return true iff o is a Score object and has the same id,
     * score and username
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * @return the hashcode of this Score.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);}

    /**
     * @return a String representation of this Object.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
