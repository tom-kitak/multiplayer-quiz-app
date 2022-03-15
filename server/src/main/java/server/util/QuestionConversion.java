package server.util;

import commons.CompareQuestion;
import commons.Activity;
import commons.Question;
import commons.WattageQuestion;
import java.util.Random;

public class QuestionConversion {

    private static Random random;
    /**
     * Method to convert activities to a Question.
     * @param activities The activities to be used.
     * @return A Question or null if input is invalid.
     */
    public static Question convertActivity(Activity[] activities) {
        Question result = null;
        if (activities != null && activities.length == 4){
            String[] titles = new String[4];
            long[] wattages = new long[4];
            if (!createTitlesAndWattages(activities,titles,wattages)) {
                return null;
            }
            result = getRandomQuestion(titles, wattages);
        }
        return result;
    }

    /**
     * Method to get a random type of question.
     * @param titles The titles to use for the question.
     * @param wattages the wattages to use for the question.
     * @return A new question.
     */
    private static Question getRandomQuestion(String[] titles, long[] wattages) {
        random = new Random();
        //bound is amount of types of questions.
        int idx = random.nextInt(2);
        //for each type of question add a new case.
        /*return switch (idx) {
            case 0 -> new CompareQuestion(titles, wattages);
            case 1 -> new WattageQuestion(titles, wattages);
            default -> throw new IllegalStateException("Unexpected value: " + idx);
        };*/
        if (idx == 0) {
            return new CompareQuestion(titles, wattages);
        } else {
            return new WattageQuestion(titles, wattages);
        }
    }

    /**
     * Method to insert titles and wattage's from the activities.
     * @param activities The activities to get the information from.
     * @param titles The array to put the question titles in.
     * @param wattages The array to put the question wattage's in.
     * @return True on success or false on fail.
     */
    private static boolean createTitlesAndWattages(Activity[] activities, String[] titles,
                                                   long[] wattages) {
        for (int i = 0; i < 4; i++){
            if (activities[i] != null) {
                Activity current = activities[i];
                if (current.getTitle() != null && current.getWh() != 0) {
                    titles[i] = current.getTitle();
                    wattages[i] = current.getWh();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
