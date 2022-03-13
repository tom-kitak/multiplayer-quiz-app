package commons;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class Question_Bryan {

    private final String[] answerTitles;
    private final long[] answerWattages;

    /**
     * The general constructor for questions.
     * @param answerTitles The answers for this question, index 0 is the correct one.
     * @param answerWattages The wattage's for this question, index 0 is the correct one.
     */
    public Question_Bryan(String[] answerTitles, long[] answerWattages) {
        if (answerTitles != null && answerWattages != null && answerTitles.length == 4 && answerWattages.length == 4) {
            this.answerTitles = answerTitles;
            this.answerWattages = answerWattages;
        } else {
            throw new IllegalArgumentException("The provided arrays were null or not length 4.");
        }
    }

    /**
     * Getter for the answer title's.
     * @return this.answerTitles.
     */
    public String[] getAnswerTitles() {
        return answerTitles;
    }

    /**
     * Getter for the answer wattage's.
     * @return this.answerWattages.
     */
    public long[] getAnswerWattages() {
        return answerWattages;
    }

    /**
     * Getter for the correct answer title.
     * @return index 0 of this.answerTitles.
     */
    public String getCorrectAnswer() {
        return answerTitles[0];
    }

    /**
     * Getter for the correct answer wattage.
     * @return index 0 of this.answerWattages.
     */
    public long getCorrectWattage() {
        return answerWattages[0];
    }

    /**
     * Equals method for this object.
     * @param o Object to be compared against.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Question_Bryan)) return false;

        Question_Bryan question = (Question_Bryan) o;

        return new EqualsBuilder().append(answerTitles, question.answerTitles)
                .append(answerWattages, question.answerWattages).isEquals();
    }

    /**
     * Hashcode generator for questions.
     * @return Hashcode of this question.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(answerTitles).append(answerWattages).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("answerTitles", answerTitles)
                .append("answerWattages", answerWattages)
                .toString();
    }
}
