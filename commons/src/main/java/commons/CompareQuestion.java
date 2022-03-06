package commons;

public class CompareQuestion extends Question_Bryan {
    /**
     * Constructor matching super for comparison question.
     * @param answers The answers for this question, index 0 is the correct one.
     * @param wattage The wattage's for this question, index 0 is the correct one.
     */
    public CompareQuestion(String[] answers, int[] wattage) {
        super(answers, wattage);
    }
}
