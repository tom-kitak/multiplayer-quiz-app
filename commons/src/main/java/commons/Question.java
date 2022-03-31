package commons;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CompareQuestion.class, name = "CompareQuestion"),
        @JsonSubTypes.Type(value = WattageQuestion.class, name = "WattageQuestion"),
        @JsonSubTypes.Type(value = OpenQuestion.class, name = "OpenQuestion")
})




public abstract class Question {

    private final String[] answerTitles;
    private final long[] answerWattages;
    public byte[] questionImage;



    /**
     * The general constructor for questions.
     * @param answerTitles The answers for this question, index 0 is the correct one.
     * @param answerWattages The wattage's for this question, index 0 is the correct one.
     */
    public Question(String[] answerTitles, long[] answerWattages, byte[] questionImage) {
        if (answerTitles != null && answerWattages != null && answerTitles.length == 4 &&
                answerWattages.length == 4 && questionImage != null) {
            this.answerTitles = Arrays.copyOf(answerTitles, answerTitles.length);
            this.answerWattages = Arrays.copyOf(answerWattages, answerWattages.length);
            this.questionImage = Arrays.copyOf(questionImage, questionImage.length);
        } else {
            throw new IllegalArgumentException("The provided arguments were null or not length 4.");
        }
    }

    /**
     * Used for transferring the questions.
     */
    public  Question() {
        this.answerTitles = null;
        this.answerWattages = null;
        this.questionImage = null;
    }

    /**
     * Getter for the question image byte[].
     * @return this.questionImage
     */
    public byte[] getQuestionImage() {
        return questionImage;
    }

    /**
     * Getter for the answer title's.
     * @return this.answerTitles.
     */
    public String[] getAnswerTitles() {
        return Arrays.copyOf(answerTitles, answerTitles.length);
    }

    /**
     * Getter for the answer wattage's.
     * @return this.answerWattages.
     */
    public long[] getAnswerWattages() {
        return Arrays.copyOf(answerWattages, answerWattages.length);
    }

    /**
     * Getter for the correct answer title.
     * @return index 0 of this.answerTitles.
     */
    @JsonIgnore
    public String getCorrectAnswer() {
        return answerTitles[0];
    }

    /**
     * Getter for the correct answer wattage.
     * @return index 0 of this.answerWattages.
     */
    @JsonIgnore
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

        if (!(o instanceof Question question)) return false;

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

    /**
     * @return a String representation of this Question object.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("answerTitles", answerTitles)
                .append("answerWattages", answerWattages)
                .toString();
    }

    /**
     * @return a String that represents the description of
     * the question that will be shown to the client
     */
    public abstract String getQuestionDescription();

    public Question QuestionWithoutImage() {
        Question result = null;
        String[] copyAnswer = Arrays.copyOf(answerTitles, answerTitles.length);
        long[] copyWattage = Arrays.copyOf(answerWattages, answerWattages.length);
        byte[] copyImage = new byte[questionImage.length];
        for(int i = 0; i < copyImage.length; i++) {
            copyImage[i] = Byte.valueOf(questionImage[i]).byteValue();
        }
        if(this instanceof CompareQuestion) {
            result = new CompareQuestion(copyAnswer,copyWattage,copyImage);
        } else if(this instanceof WattageQuestion) {
            result = new CompareQuestion(copyAnswer, copyWattage, copyImage);
        } else {
            result = new OpenQuestion(copyAnswer, copyWattage, copyImage);
        }
        result.questionImage = null;
        return result;
    }







}
