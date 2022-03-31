package commons;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class CompareQuestion extends Question {

    /**
     * Constructor matching super for comparison question.
     * @param answers The answers for this question, index 0 is the correct one.
     * @param wattage The wattage's for this question, index 0 is the correct one.
     */
    public CompareQuestion(String[] answers, long[] wattage, byte[] questionImage) {
        super(answers, wattage, questionImage);
    }

    /**
     * Used for transferring the questions.
     */
    public CompareQuestion() {}
    /**
     * @return a String that represents the description of
     * the question that will be shown to the client
     */
    @Override
    @JsonIgnore
    public String getQuestionDescription() {
        return "Which of the following activities uses the least amount of wh?";
    }

    @JsonIgnore
    public String getRightAnswer() {
        return this.getCorrectAnswer();
    }

    /**
     * Hashcode generator for questions.
     * @return Hashcode of this question.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Equals method for this object.
     * @param o Object to be compared against.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }
}
