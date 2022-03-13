package commons;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CompareQuestion extends Question {

    /**
     * Constructor matching super for comparison question.
     * @param answers The answers for this question, index 0 is the correct one.
     * @param wattage The wattage's for this question, index 0 is the correct one.
     */
    public CompareQuestion(String[] answers, long[] wattage) {
        super(answers, wattage);
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
     *
     * @return Hashcode of this question.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
