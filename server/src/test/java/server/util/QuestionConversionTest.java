package server.util;

import commons.Question;
import commons.Activity;
import commons.OpenQuestion;
import commons.CompareQuestion;
import commons.WattageQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionConversionTest {

    //Temporary string to byte[] here for testing as pipeline will other-ways fail
    String imageString = "18763671972912763726319376237108";
    byte[] tempImage = imageString.getBytes(StandardCharsets.UTF_8);

    /**
     * Only convertActivity will be tested because the other methods are only used
     * by this method and private, therefore if convertActivity can handle every possible
     * input the other methods can as well.
     */
    Activity[] activities;
    String[] titles;
    long[] wattages;

    @BeforeEach
    void setUp() {
        activities = new Activity[]{
                new Activity("activity_1", 1002, tempImage),
                new Activity("activity_2", 0, tempImage),
                new Activity("activity_3", 102302, tempImage),
                new Activity("activity_4", 10237399483L, tempImage),
                new Activity(null, 0, tempImage)
        };
        titles = new String[]{
                "activity_1",
                "activity_2",
                "activity_3",
                "activity_4",
                null
        };
        wattages = new long[]{
                1002,
                0,
                102302,
                10237399483L,
                0
        };
    }

    @RepeatedTest(20)
    void convertActivity_success(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        Question compare = new CompareQuestion(Arrays.copyOf(this.titles, 4),
                Arrays.copyOf(this.wattages, 4), tempImage);
        Question wattage = new WattageQuestion(Arrays.copyOf(this.titles, 4),
                Arrays.copyOf(this.wattages, 4), tempImage);
        Question open = new OpenQuestion(Arrays.copyOf(this.titles, 4),
                Arrays.copyOf(this.wattages, 4), tempImage);
        Question result = QuestionConversion.convertActivity(activities);
        assertTrue(result.equals(compare) || result.equals(wattage) || result.equals(open));
    }

    @Test
    void convertActivity_failure_1(){
        assertEquals(null, QuestionConversion.convertActivity(null));
    }

    @Test
    void convertActivity_failure_2(){
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }

    @Test
    void convertActivity_failure_3(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        activities[0] = null;
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }

    @Test
    void convertActivity_failure_4(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        activities[1] = null;
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }

    @Test
    void convertActivity_failure_5(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        activities[2] = null;
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }

    @Test
    void convertActivity_failure_6(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        activities[3] = null;
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }

    @Test
    void convertActivity_failure_7(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        activities[0] = this.activities[4];
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }

    @Test
    void convertActivity_failure_8(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        activities[1] = this.activities[4];
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }

    @Test
    void convertActivity_failure_9(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        activities[2] = this.activities[4];
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }

    @Test
    void convertActivity_failure_10(){
        Activity[] activities = Arrays.copyOf(this.activities, 4);
        activities[3] = this.activities[4];
        assertEquals(null, QuestionConversion.convertActivity(activities));
    }
}
