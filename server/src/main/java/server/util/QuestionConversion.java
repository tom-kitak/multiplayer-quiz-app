package server.util;

import commons.CompareQuestion;
import commons.Question_Bryan;
import server.Activity;

public class QuestionConversion {

    /**
     * Method to convert activities to a Question.
     * @param activities The activities to be used.
     * @return A Question.
     */
    public static Question_Bryan convertActivity(Activity[] activities) {
        if (activities != null && activities.length == 4){
            String[] titles = new String[4];
            int[] wattages = new int[4];
            for (int i = 0; i < 4; i++){
                if (activities[i] != null) {
                    Activity current = activities[i];
                    if (current.getTitle() != null && current.getWh() != 0) {
                        titles[i] = current.getTitle();
                        wattages[i] = current.getWh();
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            return new CompareQuestion(titles, wattages);
        }
        return null;
    }
}
