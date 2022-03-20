package server;

import commons.Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class ActivityTest {

    //Temporary string to byte[] here for testing as pipeline will other-ways fail
    String imageString = "18763671972912763726319376237108";
    byte[] tempImage = imageString.getBytes(StandardCharsets.UTF_8);
    @Test
    void getTitle() {
        Activity activity = new Activity("test", 5, tempImage);
        Assertions.assertEquals("test", activity.getTitle());
    }

    @Test
    void getValue() {
        Activity activity = new Activity("test", 5, tempImage);
        Assertions.assertEquals(5, activity.getWh());
    }

    @Test
    void setTitle() {
        Activity activity = new Activity("test", 5, tempImage);
        activity.setTitle("test2");
        Assertions.assertEquals("test2", activity.getTitle());
    }

    @Test
    void setValue() {
        Activity activity = new Activity("test", 5, tempImage);
        activity.setWh(7);
        Assertions.assertEquals(7, activity.getWh());
    }


    @Test
    void testEquals() {
        Activity activity = new Activity("test", 5, tempImage);
        Activity activity2 = new Activity("test", 5, tempImage);
        Assertions.assertEquals(activity, activity2);
        activity2.setWh(7);
        Assertions.assertNotEquals(activity, activity2);
    }

    @Test
    void testToString() {
        Activity activity = new Activity("test", 5, tempImage);
        System.out.println(activity);
        Assertions.assertNotNull(activity.toString());
    }

    @Test
    void getId() {
        Activity activity = new Activity("test", 5, tempImage);
        Assertions.assertEquals(0,activity.getId());
    }


    @Test
    void setId() {
        Activity activity = new Activity("test", 5, tempImage);
        activity.setId(7L);
        Assertions.assertEquals(7L, activity.getId());
    }
}
