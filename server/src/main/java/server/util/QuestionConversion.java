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
     * @return A Question.
     */
    public static Question convertActivity(Activity[] activities) {
        Question result = null;
        if (activities != null && activities.length == 4){
            String[] titles = new String[4];
            long[] wattages = new long[4];
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
            // Here we try to randomize the question types.
            random = new Random();
            int idx = random.nextInt(2);
            if(idx == 0) {
                result = new CompareQuestion(titles, wattages);
            } else if(idx == 1) {
                result = new WattageQuestion(titles,wattages);
            }

        }
        return result;
    }
}
