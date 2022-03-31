package commons;



import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;



public class WattageQuestion extends Question{

    /**
     * Generates new Question Object.
     * @param answers The answers for this question, index 0 is the correct one.
     * @param wattage The wattage's for this question, index 0 is the correct one.
     */
    public WattageQuestion(String[] answers, long[] wattage, byte[] questionImage) {
        super(answers, wattage, questionImage);
    }


    /**
     * Used for transferring questions.
     */
    public WattageQuestion() {}

    /**
     * @return the rightAnswer attribute of this object
     */
    @JsonIgnore
    public long getRightAnswer() {
        return this.getCorrectWattage();
    }


    /**
     * checks if two objects are equal.
     * @param o the object we want to compare this Question with
     * @return true iff o and this Question have the same right answer and
     * the same question
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WattageQuestion question1 = (WattageQuestion) o;
        return new EqualsBuilder().append(this.getAnswerTitles(), question1.getAnswerTitles())
                .append(this.getAnswerWattages(), question1.getAnswerWattages()).isEquals();
    }


    /**
     * @return a String representation of the object in Multi_Line_Style
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("answerTitles", this.getAnswerTitles())
                .append("answerWattages", this.getAnswerWattages())
                .toString();
    }

    /**
     * @return the description of this Question.
     */
    @Override
    @JsonIgnore
    public String getQuestionDescription() {
        return "How many watt hours does " + this.getAnswerTitles()[0] + " consume?";
    }

    /**
     * Hashcode generator for questions.
     * @return Hashcode of this question.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
